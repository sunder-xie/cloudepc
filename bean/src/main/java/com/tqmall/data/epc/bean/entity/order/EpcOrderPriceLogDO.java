package com.tqmall.data.epc.bean.entity.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EpcOrderPriceLogDO {
    private Integer id;

    private String operatorName;

    private Date operateTime;

    private Integer orderId;

    private String orderSn;

    private Integer orderGoodsId;

    private String goodsSn;

    private String goodsName;

    private BigDecimal goodsPriceOld;

    private BigDecimal goodsPriceNew;

}