package com.tqmall.data.epc.bean.param.cart;

import lombok.Data;

/**
 * 立即购买参数对象
 * Created by zhouheng on 16/9/5.
 */
@Data
public class PurchaseParam {

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 商品OE码
     */
    private String oeNumber;

    /**
     * 商品数量
     */
    private Integer goodsNumber;

    /**
     * 供应商id
     */
    private Integer sellerId;

    /**
     * 标准零件名称
     */
    private String partName;

    /**
     * 供应商名称
     */
    private String sellerName;

    /**
     * 供应商电话
     */
    private String sellerTel;
}
