package com.tqmall.data.epc.bean.bizBean.car;

import lombok.Data;

/**
 * Created by huangzhangting on 16/8/1.
 */
@Data
public class CarInfoBO {
    private Integer id;

    private String name;

    private String power;

    private Integer powerId;

    private Integer level;

    private String firstWord;

    private Integer pid;

    private Integer yearId;

    private String year;

    private String model;

    private Integer modelId;

    private String series;

    private Integer seriesId;

    private String brand;

    private Integer brandId;

    private String company;

    private String carType;

    private String importInfo;

    private String logo;

    private String carName;//拼装车型全称
}
