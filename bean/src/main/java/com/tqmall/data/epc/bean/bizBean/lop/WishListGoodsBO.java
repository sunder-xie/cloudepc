package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishListGoodsBO {
    private Integer id;

    //这条记录对应的品质的相同商品的id集合
    private String sameGoodsIds;

    private Integer wishListId;

    private Integer goodsQualityType;

    private String goodsName;

    private String goodsOe;

    private Integer goodsNumber;

    private String goodsMeasureUnit;

    private String goodsImages;

    private String goodsMemo;

    private Integer groupId;
    /**
     * 页面传递参数，多个品质以逗号分割，插入时候会根据这个生成多个对象
     */
    private String qualityTypeStr;
}
