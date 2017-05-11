package com.tqmall.data.epc.bean.bizBean.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单商品业务对象
 * Created by lyj on 16/8/29.
 * 开开心心写代码-lyj!
 */
@Data
public class OrderGoodsBO {

    //数据库自增ID
    private Integer id;

    //订单ID
    private Integer orderId;

    //订单编号
    private String orderSn;

    //商品
    private Integer goodsId;

    //商品编号
    private String goodsSn;

    //商品名称
    private String goodsName;

    //商品规格型号
    private String goodsFormat;

    //商品图片
    private String goodsImg;

    //商品计量单位
    private String measureUnit;

    //商品品质
    private String goodsQuality;

    //品牌名称
    private String brandName;

    //标准零件名称
    private String partName;

    //oe 码
    private String oeNumber;

    //商品购买数量
    private Integer goodsNumber;

    //商品原价
    private BigDecimal goodsPrice;

    //商品实际单价
    private BigDecimal soldPrice;

    //商品实际总金额
    private BigDecimal soldPriceAmount;

    //适用车型别称--云配使用
    private String carAlias;

}
