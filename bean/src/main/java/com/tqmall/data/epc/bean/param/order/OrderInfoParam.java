package com.tqmall.data.epc.bean.param.order;

import lombok.Data;

import java.util.List;

/**
 * 前台提交参数对象
 * Created by zhouheng on 16/8/31.
 */

@Data
public class OrderInfoParam {
    //门店名称
    private String companyName;
    //收货人
    private String consignee;
    //收货人联系方式
    private String mobile;
    //省份
    private Integer province;
    //城市
    private Integer city;
    //县
    private Integer district;
    //街道
    private Integer street;
    //详细地址
    private String address;

    //配送方式id
    private Integer shippingId;
    //配送方式名称
    private String shippingName;
    //支付方式id
    private Integer payId;
    //支付方式名称
    private String payName;
    //发票类型
    private Integer invType;
    //发票类型名称
    private String invTypeName;

    //供应商商家列表
    private List<OrderSellerInfoParam> sellerList;

    //立即购买
    private boolean buyNow = false;
}
