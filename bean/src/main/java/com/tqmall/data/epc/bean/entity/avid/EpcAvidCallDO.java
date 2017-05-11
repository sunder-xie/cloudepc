package com.tqmall.data.epc.bean.entity.avid;

import lombok.Data;

import java.util.Date;

@Data
public class EpcAvidCallDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private String modifierName;

    private Integer cityId;

    private String cityName;

    private Integer shopId;

    private Integer accountId;

    private Integer userId;

    private String shopName;

    private String shopTel;

    private String fillInPerson;

    private String fillInPersonTel;

    private Integer receiptAddressId;

    private String originalCarName;

    private Integer carBrandId;

    private Integer carSeriesId;

    private Integer carModelId;

    private Integer carPowerId;

    private Integer carYearId;

    private Integer carId;

    private String carVin;

    private Integer isModifyCar;

    private Integer invoiceType;

    private String orderRemark;

    private Integer sellerId;

    private Integer avidCallStatus;

    private String cancelReason;

    private Date turnOrderTime;

    private Integer wishListId;

    private Integer offerListId;

    private Date turnWishTime;

}