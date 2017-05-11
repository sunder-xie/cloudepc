package com.tqmall.data.epc.biz.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderGoodsBO;

import java.util.List;

/**
 * Created by zxg on 16/8/30.
 * 17:48
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface OrderGoodsBiz {

    //订单商品改价
    Result modifyOrderPrice(String orderSn, Integer sellerId, List<OrderGoodsBO> paramList,String operator);
}
