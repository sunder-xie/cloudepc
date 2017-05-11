package com.tqmall.data.epc.biz.order;

import com.tqmall.core.common.entity.Result;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderGoodsDO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderGoodsSnapshotDO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderSnapshotDO;
import com.tqmall.data.epc.bean.param.order.*;
import com.tqmall.data.epc.biz.cart.CartBiz;
import com.tqmall.data.epc.biz.chat.ChatBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.biz.sys.SysSequenceBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.InvType;
import com.tqmall.data.epc.common.bean.enums.UserType;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderDOMapper;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderGoodsDOMapper;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderGoodsSnapshotDOMapper;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderSnapshotDOMapper;
import com.tqmall.data.epc.exterior.dubbo.grace.SellerTaxExt;
import com.tqmall.data.epc.exterior.dubbo.stall.StallGoodsExt;
import com.tqmall.tqmallstall.domain.result.goods.GoodsMiniDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huangzhangting on 16/8/31.
 */
@Service
@Slf4j
public class CreateOrderBizImpl implements CreateOrderBiz {
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private SysSequenceBiz sysSequenceBiz;
    @Autowired
    private StallGoodsExt stallGoodsExt;
    @Autowired
    private EpcOrderDOMapper orderDOMapper;
    @Autowired
    private EpcOrderGoodsDOMapper orderGoodsDOMapper;
    @Autowired
    private SellerTaxExt sellerTaxExt;
    @Autowired
    private CartBiz cartBiz;
    @Autowired
    private ChatBiz chatBiz;
    @Autowired
    private EpcOrderSnapshotDOMapper orderSnapshotDOMapper;
    @Autowired
    private EpcOrderGoodsSnapshotDOMapper goodsSnapshotDOMapper;


    /** TODO 验证必要参数 */
    private boolean checkOrderInfoParam(OrderInfoParam param){
        if(param==null){
            return false;
        }
        if(CollectionUtils.isEmpty(param.getSellerList())){
            return false;
        }
        if(param.getShippingId()==null || param.getPayId()==null || param.getInvType()==null){
            return false;
        }

        return true;
    }

    @Override
    public Result submitOrder(OrderInfoParam param) {
        if(!checkOrderInfoParam(param)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        UserBO user = shiroBiz.getCurrentUser();
        //失效商品名称
        StringBuilder abateGoodsSb = new StringBuilder();
        //价格改动商品名称
        StringBuilder priceChangeGoodsSb = new StringBuilder();

        List<CreateOrderParam> paramList = new ArrayList<>();
        for(OrderSellerInfoParam sellerInfoParam : param.getSellerList()){
            /** 组装创建订单参数对象 */
            CreateOrderParam orderParam = BdUtil.do2bo(param, CreateOrderParam.class);
            orderParam.setInvTypeName(InvType.codeName(param.getInvType()));
            //增值发票
            if(InvType.ADDED_TAX_INV.getCode().equals(param.getInvType())){
                orderParam.setTaxRate(ConstantBean.ADDED_TAX_RATE);
            }else if(InvType.NORMAL_INV.getCode().equals(param.getInvType())){
                Result<BigDecimal> taxResult = sellerTaxExt.getOriginalTaxRate(sellerInfoParam.getSellerId());
                if(!taxResult.isSuccess()){
                    log.info("getOriginalTaxRate failed, result:{}", JsonUtil.objectToJson(taxResult));
                    return ResultUtil.errorResult("", "非常抱歉，税金计算异常，无法提交订单");
                }
                orderParam.setTaxRate(taxResult.getData());
            }

            //商品信息
            Result<List<CreateOrderGoodsParam>> listResult =
                    convertGoodsParam(sellerInfoParam.getGoodsParamList(), orderParam, abateGoodsSb, priceChangeGoodsSb);

            //只要有失败的情况，就直接返回
            if(!listResult.isSuccess()){
                if(abateGoodsSb.length()==0){
                    return ResultUtil.errorResult("", "订单提交失败，请重试");
                }else{
//                    abateGoodsSb.deleteCharAt(0);
//                    abateGoodsSb.append("已失效");
                    return ResultUtil.errorResult("", "部分商品已失效，请重新核对");
                }
            }
            orderParam.setGoodsParamList(listResult.getData());

            //门店信息
            orderParam.setCityId(shiroBiz.getUserCity());
            orderParam.setShopId(user.getShopId());
            orderParam.setAccountId(user.getAccountId());

            //供应商信息
            orderParam.setSellerId(sellerInfoParam.getSellerId());
            orderParam.setSellerCompanyName(sellerInfoParam.getSellerCompanyName());
            orderParam.setSellerTelephone(sellerInfoParam.getSellerTelephone());
            orderParam.setOrderNote(sellerInfoParam.getOrderNote());

            paramList.add(orderParam);
        }

        if(abateGoodsSb.length()>0){
//            abateGoodsSb.deleteCharAt(0);
//            abateGoodsSb.append("已失效");
            return ResultUtil.errorResult("", "部分商品已失效，请重新核对");
        }
        if(priceChangeGoodsSb.length()>0){
//            priceChangeGoodsSb.deleteCharAt(0);
//            priceChangeGoodsSb.append("价格发生改动，请重新核对");
            return ResultUtil.errorResult("", "商品价格发生变更，请重新核对");
        }

        return createSomeOrder(paramList);
    }

    /** 组装商品信息 */
    private Result<List<CreateOrderGoodsParam>> convertGoodsParam(List<OrderGoodsInfoParam> goodsInfoParams, CreateOrderParam orderParam,
                                                                  StringBuilder abateGoodsSb, StringBuilder priceChangeGoodsSb){
        if(CollectionUtils.isEmpty(goodsInfoParams)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        List<CreateOrderGoodsParam> goodsParamList = new ArrayList<>();
        List<Integer> goodsIds = new ArrayList<>();
        for(OrderGoodsInfoParam goodsInfoParam : goodsInfoParams){
            goodsIds.add(goodsInfoParam.getGoodsId());
            goodsParamList.add(BdUtil.do2bo(goodsInfoParam, CreateOrderGoodsParam.class));
        }

        List<GoodsMiniDTO> list = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        if(list==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }
        if(list.isEmpty()){
            for(OrderGoodsInfoParam goodsInfoParam : goodsInfoParams){
                appendMsg(abateGoodsSb, goodsInfoParam.getPartName());
            }
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        //判断云修用户
        UserBO user = shiroBiz.getCurrentUser();
        boolean yxFlag = false;
        if(UserType.YUN_XIU.getCode().equals(user.getUserTypeCode())){
            yxFlag = true;
        }

        for(CreateOrderGoodsParam goodsParam : goodsParamList){
            boolean flag = true;
            for(GoodsMiniDTO dto : list){
                if(goodsParam.getGoodsId().equals(dto.getGoodsId())){
                    flag = false;

                    if(yxFlag) {
                        BigDecimal price = dto.getYunXiuPrice();
                        if(price==null || price.compareTo(BigDecimal.ZERO)<=0){
                            price = dto.getPrice();
                        }
                        //价格改动商品
                        if(goodsParam.getGoodsPrice().compareTo(price)!=0){
                            appendMsg(priceChangeGoodsSb, goodsParam.getPartName());
                        }
                        goodsParam.setGoodsPrice(price);
                    }else{
                        //价格改动商品
                        if(goodsParam.getGoodsPrice().compareTo(dto.getPrice())!=0){
                            appendMsg(priceChangeGoodsSb, goodsParam.getPartName());
                        }
                        goodsParam.setGoodsPrice(dto.getPrice());
                    }
                    goodsParam.setBrandName(dto.getBrandName());
                    goodsParam.setGoodsName(dto.getGoodsName());
                    goodsParam.setGoodsImg(dto.getGoodsImg());
                    goodsParam.setGoodsFormat(dto.getGoodsFormat());
                    goodsParam.setGoodsQuality(dto.getGoodsQualityTypeName());
                    goodsParam.setCarAlias(dto.getCarTypeAlias());
                    goodsParam.setMeasureUnit(dto.getMeasureUnit());

                    //同一个供应商，落地仓id，目前就一个
                    if(dto.getWarehouseId()!=null) {
                        orderParam.setWarehouseId(dto.getWarehouseId());
                    }

                    break;
                }
            }
            //已失效商品
            if(flag){
                appendMsg(abateGoodsSb, goodsParam.getPartName());
            }
        }

        return ResultUtil.successResult(goodsParamList);
    }

    /** 组装信息 */
    private void appendMsg(StringBuilder sb, String name){
        sb.append("、【").append(name).append("】");
    }


    @Transactional
    @Override
    public Result<String> createOrder(CreateOrderParam param) {
        if (param == null) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<CreateOrderGoodsParam> goodsParamList = param.getGoodsParamList();
        if (CollectionUtils.isEmpty(goodsParamList)) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        //因为不对外提供创建订单接口，所以这里不再详细校验参数了

        log.info("create order, param:{}", JsonUtil.objectToJson(param));

        EpcOrderDO orderDO = BdUtil.do2bo(param, EpcOrderDO.class);
        if (orderDO == null) {
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }
        List<EpcOrderGoodsDO> goodsDOList = BdUtil.do2bo4List(goodsParamList, EpcOrderGoodsDO.class);
        if (goodsDOList.isEmpty()) {
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        Date date = orderDO.getGmtCreate();
        String orderSn = sysSequenceBiz.genOrderNo();
        if(date == null) {
            date = new Date();
            orderDO.setGmtCreate(date);
        }
        orderDO.setCreator(orderDO.getAccountId());
        orderDO.setOrderSn(orderSn);

        /** 计算商品总金额 */
        BigDecimal goodsAmount = BigDecimal.ZERO;
        BigDecimal taxRate = orderDO.getTaxRate();
        if (taxRate != null && taxRate.compareTo(BigDecimal.ZERO) > 0) {
            //比率转换
            taxRate = EPCUtil.getRate(taxRate);

            for (EpcOrderGoodsDO goodsDO : goodsDOList) {
                goodsDO.setOrderSn(orderSn);
                goodsDO.setGmtCreate(date);
                goodsDO.setCreator(orderDO.getAccountId());

                BigDecimal price = goodsDO.getGoodsPrice().multiply(BigDecimal.ONE.add(taxRate));
                //价格处理
                price = EPCUtil.getDecimalHalfUp(price);

                goodsDO.setGoodsPrice(price);
                goodsDO.setSoldPrice(price);
                goodsDO.setSoldPriceAmount(price.multiply(new BigDecimal(goodsDO.getGoodsNumber())));

                goodsAmount = goodsAmount.add(goodsDO.getSoldPriceAmount());
            }
        }else{
            for (EpcOrderGoodsDO goodsDO : goodsDOList) {
                goodsDO.setOrderSn(orderSn);
                goodsDO.setGmtCreate(date);
                goodsDO.setCreator(orderDO.getAccountId());
                goodsDO.setSoldPrice(goodsDO.getGoodsPrice());
                goodsDO.setSoldPriceAmount(goodsDO.getGoodsPrice().multiply(new BigDecimal(goodsDO.getGoodsNumber())));

                goodsAmount = goodsAmount.add(goodsDO.getSoldPriceAmount());
            }
        }
        orderDO.setGoodsAmount(goodsAmount);

        /** 根据商品金额，计算订单订单金额 */
        BigDecimal shippingFee = orderDO.getShippingFee();
        if(shippingFee != null && shippingFee.compareTo(BigDecimal.ZERO) > 0){
            orderDO.setOrderAmount(goodsAmount.add(shippingFee));
        }else{
            orderDO.setOrderAmount(goodsAmount);
        }

        /** 插入订单 */
        orderDOMapper.insertSelective(orderDO);

        /** 插入订单快照 */
        EpcOrderSnapshotDO orderSnapshotDO = BdUtil.do2bo(orderDO, EpcOrderSnapshotDO.class);
        orderSnapshotDO.setId(null);
        orderSnapshotDOMapper.insertSelective(orderSnapshotDO);

        /** 插入商品 */
        List<Integer> goodsIdList = new ArrayList<>();
        for (EpcOrderGoodsDO goodsDO : goodsDOList) {
            goodsDO.setOrderId(orderDO.getId());
            orderGoodsDOMapper.insertSelective(goodsDO);

            /** 插入商品快照 */
            EpcOrderGoodsSnapshotDO goodsSnapshotDO = BdUtil.do2bo(goodsDO, EpcOrderGoodsSnapshotDO.class);
            goodsSnapshotDO.setOrderId(orderSnapshotDO.getId());
            goodsSnapshotDO.setGmtCreate(date);
            goodsSnapshotDOMapper.insertSelective(goodsSnapshotDO);

            goodsIdList.add(goodsDO.getGoodsId());
        }

        /** 删除购物车中的商品 */
        //如果是立即购买，则不操作购物车
        if (!param.isBuyNow()) {
            cartBiz.deleteGoodsByIdList(goodsIdList);
        }

        /** IM通知供应商 */
        chatBiz.newOrderNotify(orderDO.getShopId(),orderDO.getCompanyName(), orderDO.getSellerId(),orderDO.getSellerCompanyName(),orderDO.getSellerTelephone(),
                orderSn, orderDO.getOrderAmount().toString());

        return ResultUtil.successResult(orderSn);
    }


    // TODO 事务控制记得加上
    @Transactional
    @Override
    public Result<List<String>> createSomeOrder(List<CreateOrderParam> paramList) {
        if(CollectionUtils.isEmpty(paramList)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<String> orderSnList = new ArrayList<>();

        //创建一单
        if(paramList.size()==1){
            Result<String> result = createOrder(paramList.get(0));
            if(result.isSuccess()){
                orderSnList.add(result.getData());
                return ResultUtil.successResult(orderSnList);
            }
            log.info("create order failed, result:{}", JsonUtil.objectToJson(result));
            return ResultUtil.errorResult(result);
        }

        //创建多单
        String parentOrderSn = sysSequenceBiz.genOrderNo();
        Date date = new Date();
        for(CreateOrderParam param : paramList){
            param.setGmtCreate(date);
            param.setParentOrderSn(parentOrderSn);
            Result<String> result = createOrder(param);
            if(result.isSuccess()){
                orderSnList.add(result.getData());
            }else{
                log.info("create order failed, result:{}", JsonUtil.objectToJson(result));
            }
        }
        if(orderSnList.isEmpty()){
            return ResultUtil.errorResult("", "创建订单失败");
        }
        return ResultUtil.successResult(orderSnList);
    }

}
