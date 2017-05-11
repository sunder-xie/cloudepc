package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

/**
 * 退款／退货状态记录表
 **/

@Data
public class OfferListGoodsRefundBO {

    /****/
    private Integer id;

    /****/
    private java.util.Date gmtCreate;

    /****/
    private java.util.Date gmtModified;

    /**
     * 申请人id
     **/
    private Integer creator;

    /****/
    private Integer modifier;

    /****/
    private String isDeleted;

    /****/
    private Integer sellerId;

    /**
     * 商家处理人id
     **/
    private Integer sellerLoginId;

    /**
     * 关联的报价单的id
     **/
    private Integer offerListId;

    /**
     * 关联的需求单的id
     **/
    private Integer wishListId;

    /**
     * db_offer_list_goods的商品id
     **/
    private Integer offerListGoodsId;

    /**
     * 商品退款金额
     **/
    private java.math.BigDecimal goodsPriceAmountRefund;

    /**
     * TK:退款；TH：退货;SH：售后
     **/
    private String refundType;

    /**
     * 退款退货原因
     **/
    private Integer refundReasonId;

    /**
     * 款退货原因
     **/
    private String refundReason;

    /**
     * 申请退货退款的备注
     **/
    private String refundMemo;

    /****/
    private String refundProofImage;

    /**
     * 退款：申请退款(SQTK)，退款中(TKZ)，退款完成(TKWC)；退货：申请退货(SQTH)，退货申请通过(THSQTG)，退货中（THZ），退款中（THTKZ），退货退款完成（THTKWC）
     **/
    private String refundStatus;

    /**
     * 报价单的发货状态（WFH、YFH）
     **/
    private String shippingStatus;

    /**
     * 发货方式
     **/
    private String shippingType;

    /**
     * 快递物流公司
     **/
    private String shippingCompany;

    /**
     * 快递单号
     **/
    private String shippingNo;

    /**
     * 发货时拍摄的图片
     **/
    private String shippingImage;

    /**
     * 发货时间
     **/
    private java.util.Date shippingTime;

    private OfferListBO offerListBO;

    private OfferListGoodsBO offerListGoodsBO;

}
