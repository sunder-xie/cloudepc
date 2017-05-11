package com.tqmall.data.epc.bean.param.cart;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * 购物车参数对象（参考自web）
 */
@Data
public class CartParam extends BaseQueryParam {

    /**
     * 购物车编号
     */
    private String ids;

    /**
     * 普通商品购买参数
     */
    private Integer goodsNumber;

    private Integer goodsId;

    private Byte buyNow;

    /**
     * 活动购买参数
     */
    private int activityId;

    private int activityGroupId;

    private int warehouseId;

    private String optOrderType;

}
