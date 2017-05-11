package com.tqmall.data.epc.bean.param.order.pay;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/9/2.
 */
@Data
public class AliPayFormParam {
    private String subject;

    private String body;

    private BigDecimal totalFee;

    private String sn;

    private String deviceId;

    private String webReturnUrl;


    private String source;

    private int uid;
}
