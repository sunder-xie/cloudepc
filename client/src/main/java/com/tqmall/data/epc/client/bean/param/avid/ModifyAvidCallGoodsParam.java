package com.tqmall.data.epc.client.bean.param.avid;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangzhangting on 16/10/26.
 */
@Data
public class ModifyAvidCallGoodsParam implements Serializable {
    private Integer id;

    private String goodsName;

    private String goodsOe;

    private Integer goodsNumber;

    private String goodsUnit;

    private Integer goodsQualityId; //首选品质

    private Integer backQualityId; //备选品质

    private String goodsRemark;

}
