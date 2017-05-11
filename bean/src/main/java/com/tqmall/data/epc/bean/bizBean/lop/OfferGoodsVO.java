package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class OfferGoodsVO {
    /**
     * 报价单商品ID
     */
    private Integer offerListGoodsId;
    /**
     * 报价单商品名称
     */
    private String feedbackGoodsName;
    /**
     * 报价单OE码
     */
    private String feedbackOeNum;
    /**
     * 报价单商品品质
     */
    private String feedbackGoodsQualityType;
    /**
     * 报价单商品价格（用于计算）
     */
    private BigDecimal feedbackGoodsPrice;
    /**
     * 报价单价格（用于展示）
     */
    private String feedbackGoodsPriceText;
    /**
     * 报价单商品数量，一般情况下， = 需求单商品数量
     */
    private Integer feedbackGoodsNumber;
    /**
     * 报价单商品品牌
     */
    private String feedbackGoodsBrand;
    /**
     * 需求单的分组标识
     */
    private Integer groupId;
    /**
     * 用户是否已选择这个商品
     * ！！！！！注意！！！！这个标识不能说明用户要购买这个商品
     */
    private Integer isUserSelectPay;
}
