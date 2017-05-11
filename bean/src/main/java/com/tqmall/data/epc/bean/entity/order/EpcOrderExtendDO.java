package com.tqmall.data.epc.bean.entity.order;

import lombok.Data;

import java.util.Date;

@Data
public class EpcOrderExtendDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private Integer orderId;

    private String orderSn;

    private String shippingCompany;

    private String shippingNo;

}