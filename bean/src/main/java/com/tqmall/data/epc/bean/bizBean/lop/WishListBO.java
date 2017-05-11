package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishListBO {
    private Integer id;

    private String wishListSn;

    private String status;

    private Integer userId;

    private String vin;

    private Integer isDeckVehicle;

    private Integer isModifiedVehicle;

    private Integer isReceiptPrice;

    private Integer brand;

    private Integer series;

    private Integer model;

    private Integer engine;

    private Integer year;

    private Integer tqCarModelId;

    private String companyName;

    private String telephone;

    private Integer cityId;

    private String wishListMemo;

    private Date wishStartTime;

    private Date wishEndTime;

    private String refer;

    private String deviceId;

    private String wishListMaker;
    private String wishListMakerTel;

    private String token;

    private String isNew;

    private Date sellerLastAlertTime;

    private Date currentTime;

    private List<WishListGoodsBO> goodsList;

    private String isOutTime;
}
