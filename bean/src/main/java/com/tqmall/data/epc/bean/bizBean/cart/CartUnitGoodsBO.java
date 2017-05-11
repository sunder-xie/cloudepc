package com.tqmall.data.epc.bean.bizBean.cart;

import lombok.Data;

import java.math.BigDecimal;

/**
 * （参考自web）
 */
@Data
public class CartUnitGoodsBO {

    private Integer goodsId;

    private Short catId;

    private String goodsName;

    private String activityName;

    private Short goodsActNumber;

    private Integer recId;

    private String goodsImg;

    private String numberDescription;

    private String goodsFormat;

    private Integer goodsNumber;

    private String listPriceName;

    private BigDecimal listPrice;

    private String priceName;

    private BigDecimal price;

    private String actUnit;

    private Integer activityId;

    private Integer activityGroupId;

    private String hongBaoTag;

    private BigDecimal goodsActPrice;

    private BigDecimal goodsPrice;

    private BigDecimal goodsSaveOfRedPack;

    private BigDecimal goodsSaveOfLegend;

}
