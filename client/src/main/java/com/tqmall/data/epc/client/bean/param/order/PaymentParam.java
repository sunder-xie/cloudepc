package com.tqmall.data.epc.client.bean.param.order;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangzhangting on 16/8/31.
 */
@Data
public class PaymentParam implements Serializable{
    private Integer payId;
    private String payName;
}
