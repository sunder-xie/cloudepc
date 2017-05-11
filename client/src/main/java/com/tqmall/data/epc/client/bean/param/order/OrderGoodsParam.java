package com.tqmall.data.epc.client.bean.param.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/8/29.
 */
@Data
public class OrderGoodsParam implements Serializable{
    private Integer goodsId; //epc_order_goods表自增id
    private BigDecimal soldPrice; //商品实际单价
}
