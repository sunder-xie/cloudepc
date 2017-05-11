package com.tqmall.data.epc.bean.entity.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EpcOrderSnapshotDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private Integer shopId;

    private String companyName;

    private String consignee;

    private String mobile;

    private Integer province;

    private Integer city;

    private Integer district;

    private Integer street;

    private String address;

    private Integer cityId;

    private Integer accountId;

    private Integer sellerId;

    private String sellerTelephone;

    private String sellerCompanyName;

    private Integer warehouseId;

    private String orderSn;

    private Integer orderStatus;

    private Integer shippingStatus;

    private Integer payStatus;

    private Integer settleStatus;

    private Date payTime;

    private Date shippingTime;

    private Date signTime;

    private Date autoSignTime;

    private Date settleTime;

    private Integer shippingId;

    private String shippingName;

    private Integer payId;

    private String payName;

    private Integer invType;

    private String invTypeName;

    private String orderNote;

    private BigDecimal goodsAmount;

    private BigDecimal orderAmount;

    private String payNo;

    private BigDecimal needSettleAmount;

    private BigDecimal hasSettleAmount;

    private BigDecimal taxRate;

    private BigDecimal commissionRate;

    private BigDecimal shippingFee;

    private String sellerOrderNote;

    private String parentOrderSn;

}