package com.tqmall.data.epc.bean.bizBean.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 确认发货参数对象
 * Created by huangzhangting on 16/8/26.
 */
@Data
public class ConfirmShippingBO implements Serializable{

    private Integer sellerId; //商家id

    private String orderSn; //订单编号

    private Integer shippingId; //配送方式id

    private String sellerOrderNote; //订单商家备注

    private String shippingCompany; //物流公司

    private String shippingNo; //物流单号

    private String operator; //操作人名称
    private Integer operatorSource;//操作来源
}
