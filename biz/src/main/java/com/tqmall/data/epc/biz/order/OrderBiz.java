package com.tqmall.data.epc.biz.order;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.ConfirmShippingBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderDetailBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderGoodsBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderListShowBO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import com.tqmall.data.epc.bean.bizBean.order.*;
import com.tqmall.data.epc.bean.param.order.OrderListPageParam;
import com.tqmall.data.epc.bean.param.order.pay.PayCallbackPO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangzhangting on 16/8/27.
 */
public interface OrderBiz {

    Result<EpcOrderDO> getOrderByOrderSn(String orderSn);

    /**
     * 获得订单列表
     *
     * @param param
     * @return
     */
    PagingResult<OrderListShowBO> orderPaged(OrderListPageParam param);

    //====================== START LYJ ======================//
    /**
     * 根据订单编号查询订单的商品
     * 给ERP使用的接口
     *
     * @param orderSn
     * @return
     */
    Result<List<OrderGoodsBO>> getGoodsByOrderSn(String orderSn);

    /**
     * 根据, 订单编号、shopId, 获取订单详情
     * 内部用
     *
     * @param orderSn
     * @param shopId
     * @return
     */
    Result<OrderDetailBO> getOrderDetailByShop(String orderSn, Integer shopId);

    /**
     * 根据, 订单编号、sellerId, 获取订单详情
     * 给外部用
     *
     * @param orderSn
     * @param sellerId
     * @return
     */
    Result<OrderDetailBO> getOrderDetailBySeller(String orderSn, Integer sellerId);

    /**
     * 根据, 订单编号、shopId, 获取订单详情(精简版)
     * 内部用
     *
     * @param orderSn
     * @param shopId
     * @return
     */
    Result<OrderBO> getOrderBySnAndShop(String orderSn, Integer shopId);

    /**
     * 根据订单编号查询订单信息
     * 账务中心使用
     *
     * @param orderSn
     * @return
     */
    Result<OrderBO> getOrderBySn(String orderSn);


    /**
     * 设置订单的支付方式
     * 内部
     *
     * @param orderSn
     * @param payId
     * @return
     */
    Result setPayment(String orderSn, Integer payId);

    /**
     * 根据订单编号集合、shopId获取订单详情列表
     * @param orderSns
     * @param shopId
     * @return
     */
    Result<List<OrderDetailBO>> getOrderDetailList(List<String> orderSns, Integer shopId);

    /**
     * 根据订单编号、shopId获取订单详情和他同辈的订单详情
     * @param orderSn
     * @param shopId
     * @return
     */
    Result<List<OrderDetailBO>> getSiblingOrderDetailList(String orderSn, Integer shopId);

    /**
     * 根据订单编号、shopId获取未支付的同辈订单数量
     * @param orderSn
     * @param shopId
     * @return
     */
    Result<Integer> getNoPayOrderCount(String orderSn, Integer shopId);

    /**
     * 根据父级订单编号，查询待支付订单数量
     * @param parentOrderSn
     * @param shopId
     * @return
     */
    Result<Integer> getNoPaySiblingOrderCount(String parentOrderSn, Integer shopId);

    /**
     * 获取需要自动签收的订单
     * @param startTime
     * @param endTime
     * @return
     */
    Result<List<EpcOrderDO>> getNeedAutoSignOrder(String startTime, String endTime);

    /**
     * 自动确认签收
     * @param orderSn
     * @return
     */
    Result autoConfirmReceive(String orderSn);

    //====================== END LYJ ======================//

    /*======start 操作订单状态 zxg=======*/
    //结算，by zxg
    Result confirmSettle(String orderSn, BigDecimal hasSettleAmount, String operator, Integer operatorSource);

    //确认发货，by zxg
    Result confirmShipping(ConfirmShippingBO confirmShippingBO);

    //订单取消
    Result cancelOrder(String orderSn, String cancelReason);

    /**
     * 确认收货
     * 内部 lyj
     *
     * @param orderSn
     * @return
     */
    Result confirmReceive(String orderSn);
    /*======end 操作订单状态=======*/

    /**
     * 支付回调
     *
     * @param param
     * @return
     */
    Result<OrderBO> payCallback(PayCallbackPO param);

}
