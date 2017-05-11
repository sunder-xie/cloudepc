package com.tqmall.data.epc.bean.param.avid;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangzhangting on 16/10/26.
 */
@Data
public class ModifyAvidCallPO implements Serializable {
    private String operator; //操作人

    private Integer id; //急呼数据主键id

    private Integer carBrandId;

    private Integer carSeriesId;

    private Integer carModelId;

    private Integer carPowerId;

    private Integer carYearId;

    private Integer carId;

    private String carVin;

    private Integer isModifyCar;

    private Integer invoiceType;

    private String orderRemark;

    private String shopTel;

    private String fillInPerson;

    private String fillInPersonTel;

    private List<ModifyAvidCallGoodsPO> goodsParamList;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", operator='" + operator + '\'' +
                '}';
    }
}
