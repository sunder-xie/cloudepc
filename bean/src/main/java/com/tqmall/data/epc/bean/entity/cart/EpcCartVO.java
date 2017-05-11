package com.tqmall.data.epc.bean.entity.cart;

import lombok.Data;

import java.util.List;

/**
 * Created by zhouheng on 16/8/25.
 */
@Data
public class EpcCartVO {
    //供应商id
    private Integer sellerId;
    //供应商昵称
    private String sellerCompanyName;
    //供应商电话
    private String sellerTelephone;
    //购物车中供应商的商品列表
    private List<EpcCartGoodsVO> goodsList;

    //是否可勾选
    private boolean canSelect = false;
}
