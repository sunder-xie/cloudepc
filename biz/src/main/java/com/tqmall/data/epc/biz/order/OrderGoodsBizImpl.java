package com.tqmall.data.epc.biz.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderGoodsBO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderGoodsDO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderPriceLogDO;
import com.tqmall.data.epc.biz.chat.ChatBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.OrderStatusEnums;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderDOMapper;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderGoodsDOMapper;
import com.tqmall.data.epc.dao.mapper.order.EpcOrderPriceLogDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zxg on 16/8/30.
 * 17:49
 * no bug,以后改代码的哥们，祝你好运~！！
 */


@Service
@Slf4j
public class OrderGoodsBizImpl implements OrderGoodsBiz {

    @Autowired
    private ChatBiz chatBiz;

    @Autowired
    private EpcOrderDOMapper orderDOMapper;
    @Autowired
    private EpcOrderGoodsDOMapper orderGoodsDOMapper;
    @Autowired
    private EpcOrderPriceLogDOMapper orderPriceLogDOMapper;

    @Transactional
    @Override
    public Result modifyOrderPrice(String orderSn, Integer sellerId, List<OrderGoodsBO> paramList, String operator) {
        if (StringUtils.isEmpty(orderSn) || null == sellerId || sellerId.equals(0) || paramList.isEmpty() || StringUtils.isEmpty(operator)) {
            log.info("arg_error==orderSn:{},sellerId:{},paramList:{},operator:{}",orderSn,sellerId,paramList.toString(),operator);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        EpcOrderDO searchOrderDO = new EpcOrderDO();
        searchOrderDO.setOrderSn(orderSn);
        List<EpcOrderDO> resultOrderList = orderDOMapper.selectByDO(searchOrderDO);
        if (resultOrderList.isEmpty()) {
            log.info("from sql,EpcOrderDO is null.orderSn:{}", orderSn);
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }
        EpcOrderDO epcOrderDO = resultOrderList.get(0);
        //判断是否是 这个 seller
        Integer oldSellerId = epcOrderDO.getSellerId();
        if (null == oldSellerId || !sellerId.equals(oldSellerId)) {
            log.info("orderSn:{},sellerId:{} oldSellerId:{} == not equal", orderSn, sellerId, oldSellerId);
            return ResultUtil.errorResult(EpcError.ORDER_SELLER_ERROR);
        }
        //如果状态为非0 只有 0:未付款  状态的可以改价格
        Integer nowOrderStatus = epcOrderDO.getOrderStatus();
        if (!nowOrderStatus.equals(OrderStatusEnums.NOT_PAY.getCode())) {
            log.info("orderSn:{} orderStatus:{} ==can't change price,need 0", orderSn, nowOrderStatus);
            return ResultUtil.errorResult(EpcError.ORDER_STATUS_ERROR);
        }

        //查找当前订单的所有商品
        EpcOrderGoodsDO searchDO = new EpcOrderGoodsDO();
        searchDO.setOrderSn(orderSn);
        List<EpcOrderGoodsDO> orderGoodsDOList = orderGoodsDOMapper.selectByDO(searchDO);
        if (CollectionUtils.isEmpty(orderGoodsDOList)) {
            log.info("orderSn:{},not in orderGoods'sql", orderSn);
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        Map<Integer, EpcOrderGoodsDO> goodsMap = new HashMap<>();
        Set<Integer> goodsIdSet = new HashSet<>();
        //改价的list
        List<EpcOrderGoodsDO> needChangeList = new ArrayList<>();
        List<EpcOrderPriceLogDO> logList = new ArrayList<>();

        for (EpcOrderGoodsDO epcOrderGoodsDO : orderGoodsDOList) {
            Integer goodsId = epcOrderGoodsDO.getGoodsId();
            goodsMap.put(goodsId, epcOrderGoodsDO);
            goodsIdSet.add(goodsId);
        }

        BigDecimal allGoodsAmount = BigDecimal.ZERO;
        for (OrderGoodsBO goodsBO : paramList) {
            Integer goodsId = goodsBO.getGoodsId();
            BigDecimal soldPrice = goodsBO.getSoldPrice();
            if(soldPrice == null){
                log.info("List have one's soldPrice is null");
                return ResultUtil.errorResult(EpcError.NPE_ERROR);
            }
            soldPrice = EPCUtil.getDecimalHalfUp(soldPrice);
            if (BigDecimal.ZERO.compareTo(soldPrice) > -1) {
                log.info("orderSn:{}, goodsId:{},soldPrice:{} is <= 0", orderSn, goodsId, soldPrice);
                return ResultUtil.errorResult(EpcError.AMOUNT_ERROR);
            }
            if (!goodsIdSet.contains(goodsId)) {
                log.info("orderSn:{}, goodsId:{},goodsId NOT IN THIS order", orderSn, goodsId);
                return ResultUtil.errorResult(EpcError.ORDER_GOODS_ERROR);
            }
            EpcOrderGoodsDO epcOrderGoodsDO = goodsMap.get(goodsId);


            BigDecimal goodsNumDecimal = new BigDecimal(epcOrderGoodsDO.getGoodsNumber());
            BigDecimal soldPriceAmount = soldPrice.multiply(goodsNumDecimal);
            allGoodsAmount = allGoodsAmount.add(soldPriceAmount);

            BigDecimal newSoldPrice = soldPrice;
            BigDecimal oldSoldPrice = epcOrderGoodsDO.getSoldPrice();//老的实际价格
            if (oldSoldPrice.equals(newSoldPrice)) {
                continue;
            }
            Integer orderGoodsId = epcOrderGoodsDO.getId();
            // 订单中的价格变更
            EpcOrderGoodsDO upDO = new EpcOrderGoodsDO();
            upDO.setId(orderGoodsId);
            upDO.setModifier(sellerId);
            upDO.setSoldPrice(newSoldPrice);
            upDO.setSoldPriceAmount(soldPriceAmount);
            needChangeList.add(upDO);
            //日志
            EpcOrderPriceLogDO logDO = new EpcOrderPriceLogDO();
            logDO.setOrderSn(orderSn);
            logDO.setOperatorName(operator);
            logDO.setOrderId(epcOrderGoodsDO.getOrderId());
            logDO.setOrderGoodsId(orderGoodsId);
            logDO.setGoodsName(epcOrderGoodsDO.getGoodsName());
            logDO.setGoodsSn(epcOrderGoodsDO.getGoodsSn());
            logDO.setGoodsPriceNew(newSoldPrice);
            logDO.setGoodsPriceOld(oldSoldPrice);
            logList.add(logDO);

        }

        //有变更
        if(!needChangeList.isEmpty()) {
            BigDecimal orderAmount = allGoodsAmount.add(epcOrderDO.getShippingFee());//订单总金额=商品总额+运费
//            BigDecimal trueCommissionRate = epcOrderDO.getCommissionRate().divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);//税率18.11 为百分比，除了后为 0.1811
//            BigDecimal needSettleAmount = orderAmount.multiply(BigDecimal.ONE.subtract(trueCommissionRate));//need_settle_amount=goods_sold_price*(1-commission_rate)*number
            BigDecimal needSettleAmount = orderAmount;

            EpcOrderDO upOrderDo = new EpcOrderDO();
            upOrderDo.setId(epcOrderDO.getId());
            upOrderDo.setGoodsAmount(allGoodsAmount);
            upOrderDo.setOrderAmount(orderAmount);//订单总金额=商品总额+运费
            upOrderDo.setNeedSettleAmount(needSettleAmount);
            upOrderDo.setModifier(sellerId);

            //todo 保存到数据库。需要加事务
            orderDOMapper.updateByPrimaryKeySelective(upOrderDo);
            for (EpcOrderGoodsDO upDO : needChangeList) {
                orderGoodsDOMapper.updateByPrimaryKeySelective(upDO);
            }

            for (EpcOrderPriceLogDO logDO : logList) {
                orderPriceLogDOMapper.insertSelective(logDO);
            }

            //发送通知
            chatBiz.changePriceNotify(epcOrderDO.getShopId(),sellerId,orderSn);

        }


        return ResultUtil.successResult(orderSn);
    }
}
