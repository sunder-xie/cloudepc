package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class SellerBO {

    private Integer id;

    private String sellerNick;

    private String sellerIntro;

    private String sellerMobile;

    private String sellerTel;

    private Integer sellerType;

    private String logo;

    private String logoTitle;

    private String companyName;

    private Integer provinceId;

    private Integer cityId;

    private Integer districtId;

    private Integer streetId;

    private String address;

    private String bulletin;

    private String domain;

    private Integer status;

    private String sellerServiceMobile;

    private String sellerContact;

    private Integer cityStation;

    private BigDecimal platformProfitRate;

    private BigDecimal selectBuyProfitRate;

    private String erpSn;

    private String businessLicenceImg;

    private String userGlobalId;

    private String nopassMsg;

    private Integer saleId;

    private String saleName;
}
