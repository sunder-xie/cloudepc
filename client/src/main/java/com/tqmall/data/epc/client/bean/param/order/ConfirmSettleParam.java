package com.tqmall.data.epc.client.bean.param.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 确认结算参数对象
 * Created by huangzhangting on 16/8/26.
 */
@Data
public class ConfirmSettleParam implements Serializable {

    private String orderSn; //订单编号

    private BigDecimal hasSettleAmount; //实际结算金额

    private String operator; //操作人名称

    private Integer operatorSource;//操作来源
}
