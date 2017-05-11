package com.tqmall.data.epc.bean.bizBean.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * （参考自web）
 */
@Data
public class CartUnitBO {

    private Integer unitNumber;

    private String unitName;

    private boolean editable;

    private List<CartUnitGoodsBO> goods;

    private Integer warehouseId;

    private Date gmtModified;

    private int activityId;

    private String activityName;

    private int activityGroupId;

    private String activityGroupName;

    private BigDecimal price;

    private String priceName;

    private BigDecimal listPrice;

    private String listPriceName;

    private BigDecimal saveAmountByRedPack;

    private BigDecimal saveAmountByLegend;

    private boolean legendPrice;

    private Boolean isNumberInUnit;

    private Boolean isAvailable;

    private Boolean isAct;

    private BigDecimal groupPrice;

    private Integer minNum;

    private Integer maxNum;

    private Integer sellerId;

    private String sellerNick;

    private Short delType;
}
