package com.tqmall.data.epc.client.server.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.client.bean.dto.order.OrderPayDTO;
import com.tqmall.data.epc.client.bean.param.order.PayCallbackParam;

/**
 * Created by huangzhangting on 16/8/31.
 */
public interface OrderPayService {
    /**
     * 支付回调方法
     * @param param
     * @return
     */
    Result<OrderPayDTO> payCallback(PayCallbackParam param);

    /**
     * 根据订单编号查询客户id
     * @param orderSn
     * @return
     */
    Result<Integer> getUserIdByOrderSn(String orderSn);
}
