package com.tqmall.data.epc.client.server.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.client.bean.dto.order.OrderDetailDTO;
import com.tqmall.data.epc.client.bean.dto.order.OrderGoodsDTO;
import com.tqmall.data.epc.client.bean.param.order.ConfirmSettleParam;
import com.tqmall.data.epc.client.bean.param.order.ConfirmShippingParam;
import com.tqmall.data.epc.client.bean.param.order.OrderGoodsParam;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/26.
 */
public interface OrderService {

    /**
     * 根据订单编号查询订单商品列表
     *
     * ERP使用
     *
     * @param orderSn
     * @return
     */
    Result<List<OrderGoodsDTO>> getGoodsByOrderSn(String orderSn);

    /**
     *
     * 根据订单编号查询订单详情
     *
     * 云配使用，所以必须传供应商id
     *
     * @param orderSn
     * @param sellerId
     * @return
     */
    Result<OrderDetailDTO> getDetailByOrderSn(String orderSn, Integer sellerId);

    /**
     * 订单结算接口
     *
     * ERP使用
     *
     * @return
     */
    Result confirmSettle(ConfirmSettleParam param);

    /**
     * 确认发货
     *
     * 云配使用，所以必须传供应商id
     *
     * @param param
     * @return
     */
    Result confirmShipping(ConfirmShippingParam param);

    /**
     *
     * 订单商品改价
     *
     * 云配使用，所以必须传供应商id
     *
     * @param orderSn 订单编号
     * @param sellerId 供应商 id
     * @param paramList 改价的商品列表
     * @param operator  操作人名称
     * @return
     */
    Result modifyOrderPrice(String orderSn, Integer sellerId, List<OrderGoodsParam> paramList, String operator);

}

