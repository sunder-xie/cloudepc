package com.tqmall.data.epc.bean.param.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/8/27.
 */
@Data
public class CreateOrderGoodsParam {
    private Integer goodsId;

    private String goodsSn;

    private String goodsName;

    private String goodsFormat;

    private String goodsImg;

    private String measureUnit;

    private String goodsQuality;

    private String brandName;

    private String partName;

    private String oeNumber;

    private Integer goodsNumber;

    private BigDecimal goodsPrice; //商品原价（不含税）

    private String carAlias;
}
