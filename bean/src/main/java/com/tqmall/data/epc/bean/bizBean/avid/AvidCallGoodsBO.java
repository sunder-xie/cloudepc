package com.tqmall.data.epc.bean.bizBean.avid;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class AvidCallGoodsBO{
    private Integer id;

    private Integer avidCallId;

    private String goodsName;

    private String goodsOe;

    private Integer goodsNumber;

    private String goodsUnit;

    private Integer goodsQualityId;

    private Integer goodsBrandId;

    private String goodsRemark;

    private BigDecimal goodsPrice;

    private Integer backQualityId;

}
