package com.tqmall.data.epc.bean.bizBean.goods;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/9/2.
 */
@Data
public class QuoteGoodsBO {
    private Integer id;

    private Integer goodsId;

    //商品编号
    private String newGoodsSn;
    //商品名称
    private String goodsName;
    //商品品牌
    private Short brandId;
    //商品品牌名称
    private String brandName;
    //商品品质分类
    private Integer goodsQualityType;
    //商品品质
    private String goodsQualityTypeName;
    //商品图片
    private String goodsImg;
    //商品型号
    private String goodsFormat;
    //该商户计量单位
    private String measureUnit;

    //价格
    private BigDecimal price;

    //组织 ID
    private Integer thirdPartId;
    //车型别称
    private String carTypeAlias;
    private Integer warehouseId;

    //公司名称
    private String companyName;
    //商家座机
    private String sellerTel;

    //处理后的公司名称
    private String hiddenCompanyName;

}
