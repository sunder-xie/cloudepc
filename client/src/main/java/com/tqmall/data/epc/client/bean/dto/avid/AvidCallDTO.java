package com.tqmall.data.epc.client.bean.dto.avid;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class AvidCallDTO implements Serializable {
    private Integer id;

    private Integer cityId;

    private String cityName;

    private Integer shopId;

    private Integer accountId;

    private Integer userId;

    private String shopName;

    private String shopTel;

    private String fillInPerson;

    private String fillInPersonTel;

    private Integer receiptAddressId;

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

    private Integer sellerId;

    List<AvidCallGoodsDTO> goodsList;
}
