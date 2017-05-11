package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishListVO {
    /**
     * 报价单ID
     */
    private Integer offerListId;

    /**
     * 报价单编号
     */
    private String offerListSn;

    /**
     * 需求单ID
     */
    private Integer wishListId;
    /**
     * 需求单编号
     */
    private String wishListSn;
    /**
     * 门店名称
     */
    private String companyName;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * VIN码
     */
    private String vin;
    /**
     * 车辆信息
     */
    private String carInfo;
    /**
     * 改装、套牌
     */
    private String carMemo;
    /**
     * 地址详情
     */
    private String addressInfo;
    /**
     * 是否开票
     */
    private String isReceiptPrice;
    /**
     * 发票类型
     */
    private Integer receipt;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 需求单状态编码
     */
    private String status;
    /**
     * 需求单状态编码描述
     */
    private String statusDesc;
    /**
     * 报价单状态编码
     */
    private String offerStatus;
    /**
     * 报价单状态编码描述
     */
    private String offerStatusDesc;

    /**
     * 支付方式
     */
    private String payCode;

    private BigDecimal offerAmount;

    /**
     * 需求单备注
     */
    private String wishMemo;
    /**
     * 商家名称
     */
    private String sellerName;
    /**
     * 商家id
     */
    private Integer sellerId;
    /**
     * 企业QQ
     */
    private String sellerQQNum;
    /**
     * 需求已提交秒数
     */
    private Long hadPast;

    private String wishListMaker;

    private String wishListMakerTel;

    /**
     * 需求单商品
     */
    private List<WishListGoodsVO> goodsList;

    /**
     * 报价单商品
     * 报价单ID，分组，商品列表
     */
    private Map<String, List<OfferGoodsVO>> feedbackGoodsList;

    private List<SellerBO> sellerList;

    /**
     * 报价单备注
     */
    private String offerListMemo;

    private String sellerTel;

    /**
     * 订单来源
     */
    private String refer;
}
