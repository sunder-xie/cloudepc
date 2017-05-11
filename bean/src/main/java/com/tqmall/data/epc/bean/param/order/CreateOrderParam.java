package com.tqmall.data.epc.bean.param.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huangzhangting on 16/8/27.
 */
@Data
public class CreateOrderParam {
    private Date gmtCreate;

    //门店id
    private Integer shopId;
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
    //下单城市站
    private Integer cityId;
    //账户id
    private Integer accountId;
    //供应商id
    private Integer sellerId;
    //供应商联系方式
    private String sellerTelephone;
    //供应商名称
    private String sellerCompanyName;
    //仓库id
    private Integer warehouseId;
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
    //订单客户备注
    private String orderNote;
    //税率
    private BigDecimal taxRate;

    //父级订单编码
    private String parentOrderSn;

    List<CreateOrderGoodsParam> goodsParamList;

    //立即购买
    private boolean buyNow = false;

}
