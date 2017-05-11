package com.tqmall.data.epc.bean.param.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 前台提交参数对象
 * Created by huangzhangting on 16/8/27.
 */
@Data
public class OrderGoodsInfoParam {
    private Integer goodsId;

    private String goodsSn;

    private String partName;

    private String oeNumber;

    private Integer goodsNumber;

    private BigDecimal goodsPrice;

}
