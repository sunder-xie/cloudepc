package com.tqmall.data.epc.biz.avid;

import com.tqmall.athena.domain.result.carcategory.CarCategoryDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.avid.AvidCallBO;
import com.tqmall.data.epc.bean.bizBean.avid.AvidCallGoodsBO;
import com.tqmall.data.epc.bean.bizBean.base.RegionBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListGoodsBO;
import com.tqmall.data.epc.bean.bizBean.shop.ShopBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.entity.avid.EpcAvidCallDO;
import com.tqmall.data.epc.bean.entity.avid.EpcAvidCallGoodsDO;
import com.tqmall.data.epc.bean.param.avid.*;
import com.tqmall.data.epc.bean.param.lop.WishListRequestParam;
import com.tqmall.data.epc.biz.lop.WishListCityBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.AvidCallStatus;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.dao.mapper.avid.EpcAvidCallDOMapper;
import com.tqmall.data.epc.dao.mapper.avid.EpcAvidCallGoodsDOMapper;
import com.tqmall.data.epc.exterior.dubbo.base.RegionServiceExt;
import com.tqmall.data.epc.exterior.dubbo.car.CarCategoryExt;
import com.tqmall.data.epc.exterior.dubbo.stall.lop.WishListExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Slf4j
@Service
public class AvidCallBizImpl implements AvidCallBiz {
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private RegionServiceExt regionServiceExt;
    @Autowired
    private CarCategoryExt carCategoryExt;
    @Autowired
    private EpcAvidCallDOMapper avidCallDOMapper;
    @Autowired
    private EpcAvidCallGoodsDOMapper avidCallGoodsDOMapper;
    @Autowired
    private WishListExt wishListExt;
    @Autowired
    private WishListCityBiz wishListCityBiz;

    @Override
    public Result<Integer> createAvidCall(Integer carSeriesId) {
        CarCategoryDTO carSeries = carCategoryExt.getCarById(carSeriesId);
        if(carSeries==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Integer cityId = shiroBiz.getUserCity();
        Result result = wishListCityBiz.checkCity(cityId);
        if(!result.isSuccess()){
//            return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR, wishListCityBiz.getCityNames());
            return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR, 1);
        }

        EpcAvidCallDO avidCallDO = new EpcAvidCallDO();
        avidCallDO.setCarSeriesId(carSeriesId);
        avidCallDO.setCarBrandId(carSeries.getPid());
        avidCallDO.setOriginalCarName(carSeries.getBrand()+carSeries.getName());

        UserBO user = shiroBiz.getCurrentUser();
        avidCallDO.setGmtCreate(new Date());
        avidCallDO.setCreator(user.getAccountId());
        avidCallDO.setAccountId(user.getAccountId());
        avidCallDO.setShopId(user.getShopId());
        avidCallDO.setUserId(user.getUid());

        avidCallDO.setCityId(cityId);
        RegionBO regionBO = regionServiceExt.getRegionById(cityId);
        if(regionBO==null){
            avidCallDO.setCityName(cityId+"");
        }else{
            avidCallDO.setCityName(regionBO.getRegionName());
        }
        ShopBO shopBO = user.getShopBO();
        if(shopBO!=null){
            avidCallDO.setShopName(shopBO.getCompanyName());
        }
        avidCallDO.setShopTel(user.getMobile());
        avidCallDO.setFillInPerson(user.getRealName());
        avidCallDO.setFillInPersonTel(user.getMobile());

        avidCallDOMapper.insertSelective(avidCallDO);

        return ResultUtil.successResult(avidCallDO.getId());
    }

    @Override
    public Result<AvidCallBO> getAvidCallById(Integer id) {
        if(id==null || id<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        EpcAvidCallDO avidCallDO = avidCallDOMapper.selectByPrimaryKey(id);
        if(avidCallDO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        AvidCallBO avidCallBO = BdUtil.do2bo(avidCallDO, AvidCallBO.class);
        List<EpcAvidCallGoodsDO> goodsDOList = avidCallGoodsDOMapper.getByAvidCallId(id);
        if(!goodsDOList.isEmpty()){
            List<AvidCallGoodsBO> list = BdUtil.do2bo4List(goodsDOList, AvidCallGoodsBO.class);
            avidCallBO.setGoodsList(list);
        }

        return ResultUtil.successResult(avidCallBO);
    }


    /** 取消急呼数据，参数校验 */
    private boolean checkCancelParam(CancelAvidCallPO param){
        if(param==null){
            return false;
        }
        if(param.getId()==null || param.getId()<1){
            return false;
        }
        if(StringUtils.isEmpty(param.getCancelReason())){
            return false;
        }
        if(StringUtils.isEmpty(param.getOperator())){
            return false;
        }

        return true;
    }

    @Override
    public Result cancelAvidCall(CancelAvidCallPO param) {
        if(!checkCancelParam(param)){
            log.info("cancelAvidCall illegal param:{}", param);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Integer id = param.getId();
        EpcAvidCallDO avidCallDO = avidCallDOMapper.selectByPrimaryKey(id);
        if(avidCallDO==null){
            log.info("cancelAvidCall field, data not exist, param:{}", param);
            return ResultUtil.errorResult("", "记录不存在，id:"+id);
        }
        if(AvidCallStatus.TURN_WISH.getCode().equals(avidCallDO.getAvidCallStatus())){
            log.info("cancelAvidCall field, has turn wish, param:{}", param);
            return ResultUtil.errorResult("", "已生成需求单，无法取消，id:"+id);
        }
        if(AvidCallStatus.SHOP_CANCEL.getCode().equals(avidCallDO.getAvidCallStatus())){
            log.info("cancelAvidCall field, status:shop cancel, param:{}", param);
            return ResultUtil.errorResult("", "门店已取消，不能重复取消，id:"+id);
        }

        avidCallDO = new EpcAvidCallDO();
        avidCallDO.setId(id);
        avidCallDO.setCancelReason(param.getCancelReason());
        avidCallDO.setModifierName(param.getOperator());
        avidCallDO.setGmtModified(new Date());
        avidCallDO.setAvidCallStatus(AvidCallStatus.SHOP_CANCEL.getCode());

        return ResultUtil.successResult(avidCallDOMapper.updateByPrimaryKeySelective(avidCallDO));
    }

    /** 修改急呼数据，参数校验 */
    private boolean checkModifyParam(ModifyAvidCallPO param){
        if(param==null){
            return false;
        }
        if(param.getId()==null || param.getId()<1){
            return false;
        }
        if(StringUtils.isEmpty(param.getOperator())){
            return false;
        }

        return true;
    }

    /**
     * 修改急呼数据，新增的商品数据校验
     */
    private boolean checkNewGoodsList(List<EpcAvidCallGoodsDO> goodsDOList, EpcAvidCallDO avidCallDO){
        for(EpcAvidCallGoodsDO goodsDO : goodsDOList){
            if(!checkNewGoods(goodsDO)){
                return false;
            }
            goodsDO.setId(null);
            goodsDO.setGmtModified(avidCallDO.getGmtModified());
            goodsDO.setGmtCreate(avidCallDO.getGmtModified());
            goodsDO.setModifierName(avidCallDO.getModifierName());
            goodsDO.setAvidCallId(avidCallDO.getId());

            //因为批量插入新数据，所以需要处理null属性
            goodsDO.handleNotEssentialProperty();
        }
        return true;
    }
    private boolean checkNewGoods(EpcAvidCallGoodsDO goodsDO){
        if(goodsDO==null){
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public Result modifyAvidCall(ModifyAvidCallPO param) {
        if(!checkModifyParam(param)){
            log.info("modifyAvidCall illegal param:{}", param);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Integer id = param.getId();
        EpcAvidCallDO avidCallDO = avidCallDOMapper.selectByPrimaryKey(id);
        if(avidCallDO==null){
            log.info("modifyAvidCall field, data not exist, param:{}", param);
            return ResultUtil.errorResult("", "记录不存在，id:"+id);
        }
        if(AvidCallStatus.TURN_WISH.getCode().equals(avidCallDO.getAvidCallStatus())){
            log.info("modifyAvidCall field, status:turn wish, param:{}", param);
            return ResultUtil.errorResult("", "已生成需求单，无法修改保存，id:"+id);
        }
        if(AvidCallStatus.SHOP_CANCEL.getCode().equals(avidCallDO.getAvidCallStatus())){
            log.info("modifyAvidCall field, status:shop cancel, param:{}", param);
            return ResultUtil.errorResult("", "门店已取消，无法修改保存，id:"+id);
        }

        avidCallDO = BdUtil.do2bo(param, EpcAvidCallDO.class);
        avidCallDO.setModifierName(param.getOperator());
        avidCallDO.setGmtModified(new Date());
        avidCallDO.setAvidCallStatus(AvidCallStatus.TEMP_SAVE.getCode());

        List<ModifyAvidCallGoodsPO> goodsParamList = param.getGoodsParamList();
        if(!CollectionUtils.isEmpty(goodsParamList)){
            List<EpcAvidCallGoodsDO> goodsDOList = avidCallGoodsDOMapper.getByAvidCallId(id);
            int size = goodsDOList.size();
            if(size == 0){
                goodsDOList = BdUtil.do2bo4List(goodsParamList, EpcAvidCallGoodsDO.class);
                if(goodsDOList.isEmpty()){
                    return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
                }
                if(!checkNewGoodsList(goodsDOList, avidCallDO)){
                    return ResultUtil.errorResult("", "配件信息参数校验不通过，请再次检查核对");
                }
                /** 批量插入数据 */
                avidCallGoodsDOMapper.batchInsert(goodsDOList);

            }else{
                //新增的数据
                List<EpcAvidCallGoodsDO> addGoodsList = new ArrayList<>();
                //修改的数据
                List<EpcAvidCallGoodsDO> modifyGoodsList = new ArrayList<>();
                for(ModifyAvidCallGoodsPO goodsPO : goodsParamList){
                    EpcAvidCallGoodsDO avidCallGoodsDO = BdUtil.do2bo(goodsPO, EpcAvidCallGoodsDO.class);
                    if(avidCallGoodsDO==null){
                        return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
                    }

                    Integer gpId = goodsPO.getId();
                    if(gpId==null || gpId<1){
                        addGoodsList.add(avidCallGoodsDO);
                        continue;
                    }
                    boolean isNew = true;
                    for(int i=0; i<size; i++){
                        EpcAvidCallGoodsDO goods = goodsDOList.get(i);
                        if(gpId.equals(goods.getId())){
                            modifyGoodsList.add(avidCallGoodsDO);
                            goodsDOList.remove(i);
                            size--;
                            isNew = false;
                            break;
                        }
                    }
                    if(isNew){
                        addGoodsList.add(avidCallGoodsDO);
                    }
                }

                /** 需要删除的数据 */
                for(EpcAvidCallGoodsDO goodsDO : goodsDOList){
                    goodsDO.setIsDeleted("Y");
                    modifyGoodsList.add(goodsDO);
                }

                if(!addGoodsList.isEmpty()){
                    /** 新增数据校验 */
                    if(!checkNewGoodsList(addGoodsList, avidCallDO)) {
                        return ResultUtil.errorResult("", "配件信息参数校验不通过，请再次检查核对");
                    }
                    /** 批量插入数据 */
                    avidCallGoodsDOMapper.batchInsert(addGoodsList);
                }

                /** 修改数据 */
                for(EpcAvidCallGoodsDO goodsDO : modifyGoodsList){
                    goodsDO.setGmtModified(avidCallDO.getGmtModified());
                    goodsDO.setModifierName(avidCallDO.getModifierName());
                    goodsDO.setAvidCallId(null);

                    avidCallGoodsDOMapper.updateByPrimaryKeySelective(goodsDO);
                }
            }
        }

        return ResultUtil.successResult(avidCallDOMapper.updateByPrimaryKeySelective(avidCallDO));
    }


    /** 验证参数 */
    private boolean checkTurnWishParam(TurnWishPO param) {
        if(param==null){
            return false;
        }
        if(param.getAvidCallId()==null || param.getAvidCallId()<1){
            return false;
        }
        if(StringUtils.isEmpty(param.getOperator())){
            return false;
        }
        if(CollectionUtils.isEmpty(param.getGoodsList())){
            return false;
        }

        return true;
    }
    @Override
    public Result<Integer> turnWish(TurnWishPO param) {
        if(!checkTurnWishParam(param)){
            log.info("turnWish illegal param:{}", param);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Integer id = param.getAvidCallId();
        EpcAvidCallDO avidCallDO = avidCallDOMapper.selectByPrimaryKey(id);
        if(avidCallDO==null){
            log.info("turnWish field, data not exist, param:{}", param);
            return ResultUtil.errorResult("", "急呼记录不存在，id:"+id);
        }
        if(AvidCallStatus.TURN_WISH.getCode().equals(avidCallDO.getAvidCallStatus())){
            log.info("turnWish field, status:turn wish, param:{}", param);
            return ResultUtil.errorResult("", "已生成需求单，不能重复转需求单，id:"+id);
        }
        if(AvidCallStatus.SHOP_CANCEL.getCode().equals(avidCallDO.getAvidCallStatus())){
            log.info("turnWish field, status:shop cancel, param:{}", param);
            return ResultUtil.errorResult("", "门店已取消，无法转需求单，id:"+id);
        }

        WishListRequestParam requestParam = BdUtil.do2bo(param, WishListRequestParam.class);
        requestParam.setRefer("管家急呼");
        requestParam.setUid(avidCallDO.getUserId());
        requestParam.setCityId(avidCallDO.getCityId());
        requestParam.setCompanyName(avidCallDO.getShopName());

        List<WishListGoodsBO> goodsBOList = new ArrayList<>();
        for(TurnWishGoodsPO goodsPO : param.getGoodsList()){
            WishListGoodsBO wishListGoodsBO = BdUtil.do2bo(goodsPO, WishListGoodsBO.class);
            wishListGoodsBO.setGoodsImages(""); //图片不处理的话，stall那边的列表查询接口比较恶心
            goodsBOList.add(wishListGoodsBO);
        }
        requestParam.setGoodsList(goodsBOList);

        Result<Integer> createResult = wishListExt.create(requestParam);
        log.info("turnWish result:{}", createResult);

        if(createResult.isSuccess()){
            Integer wishListId = createResult.getData();
            avidCallDO = new EpcAvidCallDO();
            avidCallDO.setId(id);
            avidCallDO.setGmtModified(new Date());
            avidCallDO.setModifierName(param.getOperator());
            avidCallDO.setAvidCallStatus(AvidCallStatus.TURN_WISH.getCode());
            avidCallDO.setWishListId(wishListId);
            avidCallDO.setTurnWishTime(avidCallDO.getGmtModified());

            avidCallDO.setCarVin(param.getVin());
            avidCallDO.setCarBrandId(param.getBrand());
            avidCallDO.setCarSeriesId(param.getSeries());
            avidCallDO.setCarModelId(param.getModel());
            avidCallDO.setCarPowerId(param.getEngine());
            avidCallDO.setCarYearId(param.getYear());
            avidCallDO.setCarId(param.getTqCarModel());
            avidCallDO.setIsModifyCar(param.getIsModifiedVehicle());

            avidCallDO.setShopTel(param.getTelephone());
            avidCallDO.setFillInPerson(param.getWishListMaker());
            avidCallDO.setFillInPersonTel(param.getWishListMakerTel());
            avidCallDO.setOrderRemark(param.getWishListMemo());

            avidCallDO.setInvoiceType(param.getIsReceiptPrice());

            avidCallDOMapper.updateByPrimaryKeySelective(avidCallDO);

            //更新急呼商品表数据
            updateAvidCallGoods(param);
        }

        return createResult;
    }
    private void updateAvidCallGoods(TurnWishPO param){
        //删除老的数据
        Integer avidCallId = param.getAvidCallId();
        avidCallGoodsDOMapper.deleteByAvidCallId(avidCallId);

        //批量插入新数据
        List<EpcAvidCallGoodsDO> list = new ArrayList<>();
        for(TurnWishGoodsPO goodsPO : param.getGoodsList()){
            EpcAvidCallGoodsDO callGoodsDO = new EpcAvidCallGoodsDO();
            callGoodsDO.setGmtCreate(new Date());
            callGoodsDO.setGmtModified(callGoodsDO.getGmtCreate());
            callGoodsDO.setModifierName(param.getOperator());
            callGoodsDO.setAvidCallId(avidCallId);
            callGoodsDO.setGoodsName(goodsPO.getGoodsName());
            callGoodsDO.setGoodsOe(goodsPO.getGoodsOe());
            callGoodsDO.setGoodsNumber(goodsPO.getGoodsNumber());
            callGoodsDO.setGoodsUnit(goodsPO.getGoodsMeasureUnit());
            callGoodsDO.setGoodsRemark(goodsPO.getGoodsMemo());

            String[] qualityArray = goodsPO.getQualityTypeStr().split(",");
            int len = qualityArray.length;
            callGoodsDO.setGoodsQualityId(Integer.valueOf(qualityArray[0]));
            if(len>1){
                callGoodsDO.setBackQualityId(Integer.valueOf(qualityArray[1]));
            }

            callGoodsDO.handleNotEssentialProperty();
            list.add(callGoodsDO);
        }
        avidCallGoodsDOMapper.batchInsert(list);
    }

    @Override
    public Result<Long> getNewDataCount(Date time) {
        long count = avidCallDOMapper.getNewDataCount(time);
        return ResultUtil.successResult(count);
    }
}
