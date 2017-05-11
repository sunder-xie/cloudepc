package com.tqmall.data.epc.bean.entity.cart;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zhouheng on 16/8/25.
 */
@Data
public class EpcCartGoodsVO {
    //数据id
    private Integer id;
    //商品id
    private Integer goodsId;
    //商品编号
    private String goodsSn;
    //标准零件名称
    private String partName;
    //商品数量
    private Integer goodsNumber;
    //商品价格
    private BigDecimal goodsPrice;
    //oe码
    private String oeNumber;
    //商品图片
    private String goodsImg;
    //商品品质类型
    private String goodsQuality;
    //品牌名称
    private String brandName;

    //是否有效商品
    private boolean available = true;

}
