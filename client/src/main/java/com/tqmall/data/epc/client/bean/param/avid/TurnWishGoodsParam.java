package com.tqmall.data.epc.client.bean.param.avid;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class TurnWishGoodsParam implements Serializable{
    private String goodsName;

    private String goodsOe;

    private Integer goodsNumber;

    private String goodsMeasureUnit;

    private String goodsMemo;

    /**
     * 页面传递参数，多个品质以逗号分割，插入时候会根据这个生成多个对象
     */
    private String qualityTypeStr;
}
