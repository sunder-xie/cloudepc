package com.tqmall.data.epc.bean.entity.cart;

import lombok.Data;

import java.util.Date;

@Data
public class EpcCartDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private Integer cityId;

    private Integer shopId;

    private Integer accountId;


    private Integer goodsId;

    private String goodsSn;

    private String partName;

    private String oeNumber;

    private Integer goodsNumber;

    private Integer sellerId;

    private String sellerTelephone;

    private String sellerCompanyName;

}