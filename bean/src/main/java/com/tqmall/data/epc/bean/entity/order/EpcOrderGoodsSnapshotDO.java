package com.tqmall.data.epc.bean.entity.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EpcOrderGoodsSnapshotDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private Integer orderId;

    private String orderSn;

    private Integer goodsId;

    private String goodsSn;

    private String goodsName;

    private String goodsFormat;

    private String goodsImg;

    private String measureUnit;

    private String goodsQuality;

    private String brandName;

    private String partName;

    private String oeNumber;

    private Integer goodsNumber;

    private BigDecimal goodsPrice;

    private BigDecimal soldPrice;

    private BigDecimal soldPriceAmount;

    private String carAlias;

}