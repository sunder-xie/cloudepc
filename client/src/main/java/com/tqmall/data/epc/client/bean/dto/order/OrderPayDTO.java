package com.tqmall.data.epc.client.bean.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/8/31.
 */
@Data
public class OrderPayDTO implements Serializable{
    private Integer payId; //支付方式id
    private Integer orderId; //订单id
    private String orderSn; //订单编号
    private BigDecimal totalFee; //支付金额

}
