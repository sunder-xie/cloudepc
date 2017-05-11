package com.tqmall.data.epc.client.bean.dto.avid;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class AvidCallGoodsDTO implements Serializable {
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
