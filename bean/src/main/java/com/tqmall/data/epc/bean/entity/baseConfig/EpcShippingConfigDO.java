package com.tqmall.data.epc.bean.entity.baseConfig;

import lombok.Data;

import java.util.Date;

@Data
public class EpcShippingConfigDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private String shippingName;

    private Integer sortIndex;

}