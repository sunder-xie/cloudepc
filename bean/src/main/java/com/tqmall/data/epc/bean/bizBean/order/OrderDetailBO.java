package com.tqmall.data.epc.bean.bizBean.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单详情业务对象
 * Created by lyj on 16/8/29.
 * 开开心心写代码-lyj!
 */
@Data
public class OrderDetailBO {

    //数据库自增ID
    private Integer id;

    //创建时间(即下单时间)
    private Date gmtCreate;

    //门店id（下单客户id）
    private Integer shopId;

    //门店名称
    private String companyName;

    //收货人名称
    private String consignee;

    //收货人手机号码
    private String mobile;

    //收货地址－省
    private Integer province;

    //收货地址－城市
    private Integer city;

    //收货地址－区
    private Integer district;

    //收货地址－街道
    private Integer street;

    //收货地址－详细信息
    private String address;

    //下单城市站id
    private Integer cityId;

    //帐户id
    private Integer accountId;

    //商家id（组织id）
    private Integer sellerId;

    //商家座机
    private String sellerTelephone;

    //商家公司名称
    private String sellerCompanyName;

    //发货仓库id（ERP仓库id）
    private Integer warehouseId;

    //订单编号
    private String orderSn;

    //订单状态：0:未付款 1:已取消 2:已付款 3:已发货 4:已签收 5:已结算
    private Integer orderStatus;

    //发货状态 0:未发货 1:已发货
    private Integer shippingStatus;

    //付款状态 0:未付款 1:已付款
    private Integer payStatus;

    //结算状态 0:未结算 1:已结算
    private Integer settleStatus;

    //订单支付完成时间
    private Date payTime;

    //订单发货时间
    private Date shippingTime;

    //确认收货时间
    private Date signTime;

    //自动确认收货时间
    private Date autoSignTime;

    //结算时间
    private Date settleTime;

    //发货方式
    private Integer shippingId;

    //发货方式名称
    private String shippingName;

    //支付方式 暂时交易中心定义
    private Integer payId;

    //支付名称
    private String payName;

    //发票类型：0:不带票 1:普通发票 2:增值发票
    private Integer invType;

    //发票类型名称
    private String invTypeName;

    //订单备注
    private String orderNote;

    //商品金额
    private BigDecimal goodsAmount;

    //订单金额
    private BigDecimal orderAmount;

    //支付商返回的流水号
    private String payNo;

    //需要给供应商的结算金额
    private BigDecimal needSettleAmount;

    //已经给供应商支付的结算金额
    private BigDecimal hasSettleAmount;

    //税率
    private BigDecimal taxRate;

    //佣金费率
    private BigDecimal commissionRate;

    //运费
    private BigDecimal shippingFee;

    //订单商家备注
    private String sellerOrderNote;

    //父级订单编码
    private String parentOrderSn;

    /* 订单商品 */
    List<OrderGoodsBO> orderGoodsBOs;

    /* 订单取消原因 */
    OrderCancelLogBO orderCancelLogBO;

    /* 发货信息 */
    OrderExtendBO orderExtendBO;

    //================ START 订单取消原因 ================//
    //收货地址－省
    private String provinceName;

    //收货地址－城市
    private String cityName;

    //收货地址－区
    private String districtName;

    //收货地址－街道
    private String streetName;
    //================ END 订单取消原因 ================//

}
