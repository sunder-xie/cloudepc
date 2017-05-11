package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.util.List;

/**
 * 订单（即报价单，不同封装形式而已）
 * 参考自 portal # WholeAutoPartsOrderBO
 * Created by huangzhangting on 16/7/31.
 */
@Data
public class WishOrderBO {

    private String createTime;

    private String payName;

    private String shippingName;

    private boolean showConfirmButton;

    private boolean showPayButton;

    private String offerTKStatus;

    private String offerTKStatusName;

    private String orderStatus;

    private String orderStatusName;

    private Integer countDownTime;

    private Integer id;

    private String offerListSn;

    private Integer wishListId;

    private String status;

    private String consignee;
    private String companyName;
    private String telephone;

    private String saleTel;

    private String payStatus;

    private String shippingStatus;

    private Integer userId;

    private Integer sellerId;

    private String sellerName;

    private String sellerQq;

    private String offerAmount;

    private String paidOfferAmount;

    private String payingTime;

    private String paidTime;

    private String payNo;

    private Integer province;

    private Integer city;

    private Integer district;

    private Integer street;

    private String address;

    private Integer payType;

    private Integer shippingType;

    private String shippingCompany;

    private String shippingNo;

    private Integer remainAutoPayTimeSeconds;

    private Integer payId;

    private String payCode;

    private String token;

    private String draft;

    private String offerAmountStr;

    private List<WishOrderGoodsBO> offerListGoodsList;

    /**
     * 发票类型
     */
    private Integer receipt;

    /**
     * 车型名称
     */
    private String carModelName;

    /**
     * 商家座机
     */
    private String sellerTel;

    /**
     * 车辆备注
     */
    private String isModifiedVehicle;

    //vin码
    private String vin;
}
