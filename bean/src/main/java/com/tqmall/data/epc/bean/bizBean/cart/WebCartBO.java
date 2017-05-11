package com.tqmall.data.epc.bean.bizBean.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车对象（参考自web）
 */
@Data
public class WebCartBO {

    private Integer sellerId;

    private String sellerNick;

    private List<CartUnitBO> units;

    private BigDecimal cartAmount;
}
