package com.tqmall.data.epc.client.bean.param.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/8/31.
 */
@Data
public class PayCallbackParam implements Serializable {
    private boolean success; //true：支付成功  false：支付失败
    private String message; //支付失败日志

    private PaymentParam payment; //支付方式对象

    private String orderSn; //订单编号

    private String tradeNo; //支付流水号

    private BigDecimal totalFee; //支付金额
}
