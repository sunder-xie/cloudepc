package com.tqmall.data.epc.bean.param.order;

import lombok.Data;

import java.util.List;

/**
 * 前台提交参数对象
 * Created by zhouheng on 16/8/31.
 */
@Data
public class OrderSellerInfoParam {
    //供应商id
    private Integer sellerId;
    //供应商联系方式
    private String sellerTelephone;
    //供应商名称
    private String sellerCompanyName;
    //订单客户备注
    private String orderNote;

    //供应商商品列表
    private List<OrderGoodsInfoParam> goodsParamList;
}
