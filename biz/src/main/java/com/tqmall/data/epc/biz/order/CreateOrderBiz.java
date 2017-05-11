package com.tqmall.data.epc.biz.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.param.order.CreateOrderParam;
import com.tqmall.data.epc.bean.param.order.OrderInfoParam;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/31.
 */
public interface CreateOrderBiz {

    /**
     * 前台提交订单
     * @return
     */
    Result submitOrder(OrderInfoParam param);

    /**
     * 创建订单
     *
     * @param param
     * @return
     */
    Result<String> createOrder(CreateOrderParam param);

    /**
     * 创建多个订单
     *
     * @param paramList
     * @return
     */
    Result<List<String>> createSomeOrder(List<CreateOrderParam> paramList);

}
