package com.tqmall.data.epc.bean.param.cart;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * 商品校验参数封装
 *
 * Created by huangzhangting on 16/9/8.
 */
@Data
public class CheckGoodsParam {
    private Integer goodsId;
    private String partName;
    private BigDecimal goodsPrice;
}
