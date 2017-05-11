package com.tqmall.data.epc.biz.cart;

import com.tqmall.athena.domain.result.center.car.CenterCarDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartDO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartGoodsVO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartVO;
import com.tqmall.data.epc.bean.param.cart.CheckGoodsParam;
import com.tqmall.data.epc.bean.param.cart.PurchaseParam;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.UserType;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.dao.mapper.cart.EpcCartDOMapper;
import com.tqmall.data.epc.exterior.dubbo.car.CenterCarExt;
import com.tqmall.data.epc.exterior.dubbo.goods.CenterGoodsExt;
import com.tqmall.data.epc.exterior.dubbo.stall.StallGoodsExt;
import com.tqmall.tqmallstall.domain.result.goods.GoodsMiniDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huangzhangting on 16/8/24.
 */
@Service
@Slf4j
public class CartBizImpl implements CartBiz {
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private CenterGoodsExt centerGoodsExt;
    @Autowired
    private CenterCarExt centerCarExt;
    @Autowired
    private StallGoodsExt stallGoodsExt;

    @Autowired
    private EpcCartDOMapper cartDOMapper;


    //TODO 验证必要属性
    private boolean checkAddGoods(EpcCartDO cartGoods){
        if(cartGoods==null){
            return false;
        }
        if(cartGoods.getGoodsId()==null || cartGoods.getGoodsId()<1){
            return false;
        }
        if(cartGoods.getGoodsNumber()==null || cartGoods.getGoodsNumber()<1){
            return false;
        }
        if(StringUtils.isEmpty(cartGoods.getGoodsSn())){
            return false;
        }


        if(cartGoods.getSellerId()==null || cartGoods.getSellerId()<1){
            return false;
        }


        return true;
    }

    @Override
    public Result addGoods(EpcCartDO cart) {
        if(!checkAddGoods(cart)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        //后续操作都会做数据校验，这里就不校验了，这样，入车速度更快
//        List<Integer> goodsIds = new ArrayList<>();
//        goodsIds.add(cart.getGoodsId());
//        List<GoodsMiniDTO> goodsMiniDTOs = stallGoodsExt.getGoodsMiniByIds(goodsIds);
//        if(goodsMiniDTOs==null){
//            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
//        }
//        if(goodsMiniDTOs.isEmpty()){
//            return ResultUtil.errorResult("", "【"+cart.getPartName()+"】已失效");
//        }

        cart.setShopId(user.getShopId());
        cart.setCityId(shiroBiz.getUserCity());

        EpcCartDO cartDO = cartDOMapper.selectCartGoods(cart);
        if(cartDO==null){
            cart.setAccountId(user.getAccountId());
            cart.setGmtCreate(new Date());
            cart.setCreator(user.getAccountId());
            cartDOMapper.insertSelective(cart);
        }else{
            cartDO.setGmtModified(new Date());
            cartDO.setModifier(user.getAccountId());
            cartDO.setGoodsNumber(cart.getGoodsNumber() + cartDO.getGoodsNumber());
            cartDOMapper.updateByPrimaryKeySelective(cartDO);
        }

        return ResultUtil.successResult(1);
    }

    @Override
    public Result deleteGoods(Integer goodsId) {
        Result<EpcCartDO> result = checkCartGoodsForModify(goodsId);
        if(!result.isSuccess()){
            return result;
        }

        EpcCartDO cartDO = result.getData();
        cartDO.setIsDeleted("Y");
        cartDOMapper.updateByPrimaryKeySelective(cartDO);

        return ResultUtil.successResult(1);
    }

    @Transactional
    @Override
    public Result deleteGoodsByIdList(List<Integer> goodsIdList) {
        if(CollectionUtils.isEmpty(goodsIdList)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        for(Integer goodsId : goodsIdList){
            deleteGoods(goodsId);
        }
        return ResultUtil.successResult(1);
    }

    //修改购物车时，查询数据使用
    private Result<EpcCartDO> checkCartGoodsForModify(Integer goodsId){
        if(goodsId==null || goodsId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }
        //确保是自己购物车里面的商品
        EpcCartDO cart = new EpcCartDO();
        cart.setShopId(user.getShopId());
        cart.setCityId(shiroBiz.getUserCity());
        cart.setGoodsId(goodsId);

        EpcCartDO cartDO = cartDOMapper.selectCartGoods(cart);
        if(cartDO==null){
            return ResultUtil.errorResult("", "您的购物车中已经没有该商品，请刷新购物车查看最新数据");
        }
        cartDO.setModifier(user.getAccountId());
        cartDO.setGmtModified(new Date());
        return ResultUtil.successResult(cartDO);
    }

    @Override
    public Result modifyGoodsNumber(Integer goodsId, Integer goodsNumber) {
        if(goodsNumber==null || goodsNumber<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Result<EpcCartDO> result = checkCartGoodsForModify(goodsId);
        if(!result.isSuccess()){
            return result;
        }

        EpcCartDO cartDO = result.getData();
        cartDO.setGoodsNumber(goodsNumber);
        cartDOMapper.updateByPrimaryKeySelective(cartDO);

        return ResultUtil.successResult(1);
    }

    @Override
    public Result<List<EpcCartVO>> getCartGoodsList() {
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        EpcCartDO cart = new EpcCartDO();
        cart.setShopId(user.getShopId());
        cart.setCityId(shiroBiz.getUserCity());

        List<EpcCartDO> cartGoodsList = cartDOMapper.selectCartGoodsList(cart);
        if(cartGoodsList.isEmpty()){
            return ResultUtil.errorResult("", "您还没有选购商品");
        }

        /*
        * 1、根据供应商分组
        * 2、去掉前端不需要的属性
        * 3、调接口组装价格信息
        *
        * */
        List<EpcCartVO> epcCartVOList = new ArrayList<>();

        for(EpcCartDO epcCartDO : cartGoodsList){
            if(epcCartVOList.size() > 0 ){
                boolean findFlag = false;
                for(EpcCartVO epcCartVO : epcCartVOList){
                    if(epcCartVO.getSellerId().equals(epcCartDO.getSellerId())) {

                        EpcCartGoodsVO epcCartGoodsVO = new EpcCartGoodsVO();
                        epcCartGoodsVO.setGoodsId(epcCartDO.getGoodsId());
                        epcCartGoodsVO.setPartName(epcCartDO.getPartName());
                        //组装商品数据
                        packageCartGoods(epcCartGoodsVO, epcCartVO);

                        epcCartGoodsVO.setId(epcCartDO.getId());
                        epcCartGoodsVO.setGoodsSn(epcCartDO.getGoodsSn());
                        epcCartGoodsVO.setGoodsNumber(epcCartDO.getGoodsNumber());
                        epcCartGoodsVO.setOeNumber(epcCartDO.getOeNumber());

                        epcCartVO.getGoodsList().add(epcCartGoodsVO);
                        findFlag = true;
                        break;
                    }
                }
                if(!findFlag){
                    packageCartVO(epcCartVOList, epcCartDO);
                }
            }else{
                packageCartVO(epcCartVOList, epcCartDO);
            }
        }

        return Result.wrapSuccessfulResult(epcCartVOList);
    }

    /** 组装购物车数据 */
    private void packageCartVO(List<EpcCartVO> epcCartVOList, EpcCartDO epcCartDO){
        EpcCartVO epcCartVO = new EpcCartVO();

        EpcCartGoodsVO epcCartGoodsVO = new EpcCartGoodsVO();
        epcCartGoodsVO.setGoodsId(epcCartDO.getGoodsId());
        epcCartGoodsVO.setPartName(epcCartDO.getPartName());

        packageCartGoods(epcCartGoodsVO, epcCartVO);

        List<EpcCartGoodsVO> list =  new ArrayList<>();
        epcCartGoodsVO.setId(epcCartDO.getId());
        epcCartGoodsVO.setGoodsSn(epcCartDO.getGoodsSn());
        epcCartGoodsVO.setGoodsNumber(epcCartDO.getGoodsNumber());
        epcCartGoodsVO.setOeNumber(epcCartDO.getOeNumber());

        list.add(epcCartGoodsVO);

        epcCartVO.setSellerId(epcCartDO.getSellerId());
        epcCartVO.setSellerCompanyName(epcCartDO.getSellerCompanyName());
        epcCartVO.setSellerTelephone(epcCartDO.getSellerTelephone());

        epcCartVO.setGoodsList(list);

        epcCartVOList.add(epcCartVO);
    }
    private void packageCartGoods(EpcCartGoodsVO epcCartGoodsVO, EpcCartVO epcCartVO){
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(epcCartGoodsVO.getGoodsId());

        List<GoodsMiniDTO> list = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        if(CollectionUtils.isEmpty(list)){
            epcCartGoodsVO.setAvailable(false);
            return;
        }

        GoodsMiniDTO goodsMiniDTO = list.get(0);
        epcCartGoodsVO.setGoodsImg(goodsMiniDTO.getGoodsImg());
        epcCartGoodsVO.setGoodsQuality(goodsMiniDTO.getGoodsQualityTypeName());
        epcCartGoodsVO.setBrandName(goodsMiniDTO.getBrandName());

        epcCartGoodsVO.setGoodsPrice(getGoodsPrice(goodsMiniDTO));

        epcCartVO.setCanSelect(true);
    }


    /** 结算页面使用 */
    //组装购物车数据
    private void constructCartVO(List<EpcCartVO> epcCartVOList, EpcCartDO epcCartDO){

        EpcCartGoodsVO epcCartGoodsVO = new EpcCartGoodsVO();
        epcCartGoodsVO.setGoodsId(epcCartDO.getGoodsId());
        epcCartGoodsVO.setPartName(epcCartDO.getPartName());
        //组装商品数据
        if(constructGoods(epcCartGoodsVO)){

            List<EpcCartGoodsVO> list =  new ArrayList<>();
            epcCartGoodsVO.setId(epcCartDO.getId());
            epcCartGoodsVO.setGoodsSn(epcCartDO.getGoodsSn());
            epcCartGoodsVO.setGoodsNumber(epcCartDO.getGoodsNumber());
            epcCartGoodsVO.setOeNumber(epcCartDO.getOeNumber());

            list.add(epcCartGoodsVO);

            EpcCartVO epcCartVO = new EpcCartVO();
            epcCartVO.setSellerId(epcCartDO.getSellerId());
            epcCartVO.setSellerCompanyName(epcCartDO.getSellerCompanyName());
            epcCartVO.setSellerTelephone(epcCartDO.getSellerTelephone());

            epcCartVO.setGoodsList(list);

            epcCartVOList.add(epcCartVO);
        }
    }
    //组装商品数据
    private boolean constructGoods(EpcCartGoodsVO epcCartGoodsVO){
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(epcCartGoodsVO.getGoodsId());

        List<GoodsMiniDTO> list = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }

        GoodsMiniDTO goodsMiniDTO = list.get(0);
        epcCartGoodsVO.setGoodsImg(goodsMiniDTO.getGoodsImg());
        epcCartGoodsVO.setGoodsQuality(goodsMiniDTO.getGoodsQualityTypeName());
        epcCartGoodsVO.setBrandName(goodsMiniDTO.getBrandName());

        epcCartGoodsVO.setGoodsPrice(getGoodsPrice(goodsMiniDTO));

        return true;
    }

    //验证购物车是不是当前用户的
    private boolean checkCart(EpcCartDO epcCartDO){
        if(epcCartDO==null){
            return false;
        }
        UserBO user = shiroBiz.getCurrentUser();
        return user.getShopId().equals(epcCartDO.getShopId()) && shiroBiz.getUserCity().equals(epcCartDO.getCityId());
    }

    /** 核对订单信息页面使用 */
    @Override
    public Result<List<EpcCartVO>> getGoodsListByIds(String idStr){
        if(StringUtils.isEmpty(idStr)){
            ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        UserBO user = shiroBiz.getCurrentUser();
        if(user == null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }
        String[] ids = idStr.split(",");
        List<String> idList = Arrays.asList(ids);

        /*
        * 1、根据供应商分组
        * 2、去掉前端不需要的属性
        * 3、调接口组装价格信息
        *
        * */
        List<EpcCartVO> epcCartVOList = new ArrayList<>();
        for(int i=0;i<idList.size();i++){
            EpcCartDO epcCartDO = cartDOMapper.selectByPrimaryKey(Integer.valueOf(idList.get(i)));
            if(!checkCart(epcCartDO)){
                log.info("check cart failed, id:{}, city:{}, shop:{}", idList.get(i), shiroBiz.getUserCity(), user.getShopId());
                return ResultUtil.errorResult("", "您的购物车中已经没有该商品，请刷新购物车查看最新数据");
            }

            if(epcCartVOList.size() > 0 ){
                boolean findFlag = false;
                for(EpcCartVO epcCartVO : epcCartVOList){
                    if(epcCartVO.getSellerId().equals(epcCartDO.getSellerId())) {

                        EpcCartGoodsVO epcCartGoodsVO = new EpcCartGoodsVO();
                        epcCartGoodsVO.setGoodsId(epcCartDO.getGoodsId());
                        epcCartGoodsVO.setPartName(epcCartDO.getPartName());
                        //组装商品数据
                        if(constructGoods(epcCartGoodsVO)){

                            epcCartGoodsVO.setId(epcCartDO.getId());
                            epcCartGoodsVO.setGoodsSn(epcCartDO.getGoodsSn());
                            epcCartGoodsVO.setGoodsNumber(epcCartDO.getGoodsNumber());
                            epcCartGoodsVO.setOeNumber(epcCartDO.getOeNumber());

                            epcCartVO.getGoodsList().add(epcCartGoodsVO);
                        }
                        findFlag = true;
                        break;
                    }
                }
                if(!findFlag){
                    constructCartVO(epcCartVOList, epcCartDO);
                }
            }else{
                constructCartVO(epcCartVOList, epcCartDO);
            }
        }

        return Result.wrapSuccessfulResult(epcCartVOList);
    }

    @Override
    public Result<List<EpcCartVO>> getGoodsInfoList(PurchaseParam purchaseParam) {
        if(purchaseParam==null || purchaseParam.getGoodsId()==null || purchaseParam.getSellerId()==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //获取报价商品信息
        List<Integer> goodsIdList = new ArrayList<>();
        goodsIdList.add(purchaseParam.getGoodsId());
        List<GoodsMiniDTO> goodsMiniList = stallGoodsExt.getGoodsMiniByIds(goodsIdList);
        if(goodsMiniList==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        List<EpcCartVO> cartVOList = new ArrayList<>();
        List<EpcCartGoodsVO> goodsList = new ArrayList<>();
        for(GoodsMiniDTO goodsMiniDTO : goodsMiniList){
            if(purchaseParam.getGoodsId().equals(goodsMiniDTO.getGoodsId())){
                EpcCartVO cartVO = new EpcCartVO();
                EpcCartGoodsVO cartGoodsVO = new EpcCartGoodsVO();
                cartGoodsVO.setId(goodsMiniDTO.getId());
                cartGoodsVO.setGoodsId(goodsMiniDTO.getGoodsId());
                cartGoodsVO.setGoodsSn(goodsMiniDTO.getNewGoodsSn());
                cartGoodsVO.setPartName(purchaseParam.getPartName());
                cartGoodsVO.setGoodsNumber(purchaseParam.getGoodsNumber());
                cartGoodsVO.setOeNumber(purchaseParam.getOeNumber());
                cartGoodsVO.setGoodsImg(goodsMiniDTO.getGoodsImg());
                cartGoodsVO.setGoodsQuality(goodsMiniDTO.getGoodsQualityTypeName());
                cartGoodsVO.setBrandName(goodsMiniDTO.getBrandName());

                cartGoodsVO.setGoodsPrice(getGoodsPrice(goodsMiniDTO));

                goodsList.add(cartGoodsVO);

                cartVO.setSellerId(purchaseParam.getSellerId());
                cartVO.setSellerCompanyName(purchaseParam.getSellerName());
                cartVO.setSellerTelephone(purchaseParam.getSellerTel());
                cartVO.setGoodsList(goodsList);
                cartVOList.add(cartVO);
                break;
            }
        }
        return Result.wrapSuccessfulResult(cartVOList);
    }

    //根据用户类型获取商品价格
    private BigDecimal getGoodsPrice(GoodsMiniDTO goodsMiniDTO){
        //判断云修用户
        UserBO user = shiroBiz.getCurrentUser();
        if(UserType.YUN_XIU.getCode().equals(user.getUserTypeCode())) {
            BigDecimal price = goodsMiniDTO.getYunXiuPrice();
            if(price!=null && price.compareTo(BigDecimal.ZERO)>0){
                return price;
            }
        }
        return goodsMiniDTO.getPrice();
    }

    @Override
    public Result getCartGoodsAmount() {
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        EpcCartDO cart = new EpcCartDO();
        cart.setShopId(user.getShopId());
        cart.setCityId(shiroBiz.getUserCity());

        return ResultUtil.successResult(cartDOMapper.countCartGoodsAmount(cart));
    }

    @Override
    public List<CenterCarBO> getCarForGoods(Integer goodsId){
        if(goodsId==null || goodsId<1){
            return null;
        }

        List<CenterGoodsCarDTO> goodsCarDTOList = centerGoodsExt.getGoodsCarByGoodsCar(goodsId, null);
        if(goodsCarDTOList.isEmpty()){
            return null;
        }
        List<CenterCarBO> centerCarBOList = new ArrayList<>();
        Set<Integer> modelIdSet = new HashSet<>();
        Set<Integer> carIdSet = new HashSet<>();
        for(CenterGoodsCarDTO goodsCar : goodsCarDTOList){
            //组装车型map
            if(modelIdSet.add(goodsCar.getModelId())){
                CenterCarDTO carModel = centerCarExt.getCenterCarById(goodsCar.getModelId());
                if(carModel==null)
                    continue;
            }

            //组装车款map
            if(carIdSet.add(goodsCar.getCarId())){
                CenterCarDTO car = centerCarExt.getCenterCarById(goodsCar.getCarId());
                if(car==null){
                    continue;
                }
                CenterCarBO centerCarBO = new CenterCarBO();
                centerCarBO.setId(goodsCar.getCarId());
                centerCarBO.setSeries(car.getSeries());
                centerCarBO.setBrand(car.getBrand());
                centerCarBO.setModel(car.getModel());
                centerCarBO.setPower(car.getPower());
                centerCarBO.setYear(car.getYear());
                centerCarBO.setName(car.getYear()+"款 "+car.getName());
                centerCarBOList.add(centerCarBO);
            }
        }

        return centerCarBOList;
    }

    @Override
    public List<CenterCarBO> getCarForGoodsByOeNum(String oeNum) {
        if (StringUtils.isEmpty(oeNum)) {
            return null;
        }

        CenterGoodsDTO centerGoodsDTO = centerGoodsExt.getGoodsByOeNumber(oeNum);
        if (centerGoodsDTO == null) {
            return null;
        } else {
            Integer goodsId = centerGoodsDTO.getId();
            return getCarForGoods(goodsId);
        }
    }

    @Override
    public Result deleteUnAvailableGoods() {
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        EpcCartDO cart = new EpcCartDO();
        cart.setShopId(user.getShopId());
        cart.setCityId(shiroBiz.getUserCity());

        List<Integer> goodsIds = cartDOMapper.selectGoodsIds(cart);
        if(goodsIds.isEmpty()){
            return ResultUtil.errorResult("", "您还没有选购商品");
        }

        List<GoodsMiniDTO> goodsMiniDTOs = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        if(goodsMiniDTOs==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }
        for(GoodsMiniDTO miniDTO : goodsMiniDTOs){
            if(goodsIds.contains(miniDTO.getGoodsId())){
                goodsIds.remove(miniDTO.getGoodsId());
            }
        }

        if(goodsIds.isEmpty()){
            return ResultUtil.errorResult("", "您的购物车中，暂无失效的商品");
        }

        deleteGoodsByIdList(goodsIds);

        return ResultUtil.successResult(1);
    }

    @Override
    public Result checkGoodsForSettlement(List<CheckGoodsParam> paramList) {
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        if(CollectionUtils.isEmpty(paramList)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        StringBuilder msg = new StringBuilder();

        /** 组装购物车中已删除商品 */
        EpcCartDO cart = new EpcCartDO();
        cart.setCityId(shiroBiz.getUserCity());
        cart.setShopId(user.getShopId());

        List<Integer> goodsIds = new ArrayList<>();
        for(CheckGoodsParam param : paramList){
            if(param.getGoodsId()!=null) {
                goodsIds.add(param.getGoodsId());

                cart.setGoodsId(param.getGoodsId());
                EpcCartDO epcCartDO = cartDOMapper.selectCartGoods(cart);
                if(epcCartDO==null){
                    msg.append("、【").append(param.getPartName()).append("】");
                }
            }
        }

        if(msg.length()>0){
            msg.deleteCharAt(0);
            msg.append("已删除");
            return ResultUtil.errorResult("", msg.toString());
        }

        if(goodsIds.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        /** 组装已失效商品 */
        List<GoodsMiniDTO> goodsMiniDTOs = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        if(goodsMiniDTOs==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        for(GoodsMiniDTO miniDTO : goodsMiniDTOs){
            if(goodsIds.contains(miniDTO.getGoodsId())){
                goodsIds.remove(miniDTO.getGoodsId());
            }
        }

        //如果全部查到了，则返回成功
        if(goodsIds.isEmpty()){
            return ResultUtil.successResult(1);
        }

        Set<String> unavailableGoods = new HashSet<>();
        for(CheckGoodsParam param : paramList){
            if(goodsIds.contains(param.getGoodsId())){
                unavailableGoods.add(param.getPartName());
            }
        }

        for(String str : unavailableGoods){
            msg.append("、【").append(str).append("】");
        }
        msg.deleteCharAt(0);
        msg.append("已失效");

        return ResultUtil.errorResult("", msg.toString());
    }

    @Override
    public Result checkGoodsForBuyNow(CheckGoodsParam param) {
        if(param==null || param.getGoodsId()==null || param.getGoodsPrice()==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(param.getGoodsId());
        List<GoodsMiniDTO> goodsMiniDTOs = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        if(goodsMiniDTOs==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }
        if(goodsMiniDTOs.isEmpty()){
            return ResultUtil.errorResult("", "【"+param.getPartName()+"】已失效");
        }
        GoodsMiniDTO dto = goodsMiniDTOs.get(0);
        if(param.getGoodsPrice().compareTo(getGoodsPrice(dto))!=0){
            return ResultUtil.errorResult("", "【" + param.getPartName() + "】价格发生改动");
        }

        return ResultUtil.successResult(1);
    }

}
