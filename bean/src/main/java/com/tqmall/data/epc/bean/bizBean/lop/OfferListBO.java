package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class OfferListBO {

    private Integer uid;

    private Integer id;

    private String offerListSn;

    private Integer wishListId;

    private String status;

    private String saleTel;

    private String payStatus;

    private String shippingStatus;

    private String settleStatus;

    private Date settleTime;

    private String receiveStatus;

    private Date receiveTime;

    private Integer userId;

    private Integer sellerId;

    private String sellerName;

    private String sellerQq;

    private BigDecimal offerAmount;

    private BigDecimal paidOfferAmount;

    private Date payingTime;

    private Date paidTime;

    private String payNo;

    private Integer province;

    private Integer city;

    private Integer district;

    private Integer street;

    private String address;

    private Integer payId;

    private Integer shippingType;

    private String shippingCompany;

    private String shippingNo;

    private Date autoPayTime;

    private String token;

    private String draft;

    private String consignee;

    private String companyName;

    private String telephone;

    private BigDecimal needSettleAmount;

    private BigDecimal hasSettleAmount;

    private BigDecimal needRefundAmount;

    private BigDecimal hasRefundAmount;

    private BigDecimal commissionRate;

    private String offerListMemo;

    private String isNew;

    private String offerAmountStr;

    private List<OfferListGoodsBO> offerListGoodsList;

    private OfferListActionReasonBO actionReason;
    /**
     * 最早发货
     */
    private OfferListStatusRecordBO earliestDeliver;
    /**
     * 最早签收
     */
    private OfferListStatusRecordBO earliestReceipt;

    private Date shippingTime;

    private Date createOfferTime;
    /**
     * 退款
     */
    private OfferListGoodsRefundBO refundDto;

    private String carModelName;

    private OfferListGoodsBO offerListGoodsBO;

    private Integer goodsNumber;

}
