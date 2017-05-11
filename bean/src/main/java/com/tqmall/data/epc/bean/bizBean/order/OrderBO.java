package com.tqmall.data.epc.bean.bizBean.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单详情业务对象(精简版)
 * Created by lyj on 16/9/1.
 * 开开心心写代码-lyj!
 */
@Data
public class OrderBO {
    private Integer id;

    //订单编号
    private String orderSn;

    //订单金额
    private BigDecimal orderAmount;

    //订单状态
    private Integer orderStatus;

    //门店id
    private Integer shopId;

    //支付方式id
    private Integer payId;

    //支付状态
    private Integer payStatus;

    //父级订单编码
    private String parentOrderSn;

}
