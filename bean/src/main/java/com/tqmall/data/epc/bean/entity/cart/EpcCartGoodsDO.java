package com.tqmall.data.epc.bean.entity.cart;

import lombok.Data;

import java.util.Date;

@Data
public class EpcCartGoodsDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private Integer cartId;

    private Integer goodsId;

    private String goodsSn;

//    private String goodsName;
//
//    private String goodsFormat;
//
//    private String goodsImg;
//
//    private String measureUnit;
//
//    private Integer qualityType;
//
//    private String brandName;

    private String partName;

    private String oeNumber;

    private Integer goodsNumber;

//    private String carAlias;

    private Integer sellerId;

    private String sellerTelephone;

    private String sellerCompanyName;

//    private Integer warehouseId;

}