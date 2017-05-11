package com.tqmall.data.epc.bean.bizBean.car;

import lombok.Data;

/**
 * Created by zxg on 16/2/16.
 * 11:49
 * 线上carBO
 */
@Data
public class CarBO {
    private Integer id; //level 6级别id
    private String name; //level 6级别名称
    private Integer yearId;
    private String year;
    private Integer powerId;
    private String power;
    private Integer modelId;
    private String model;
    private Integer seriesId;
    private String series;
    private Integer brandId;
    private String brand;
    private String carName; //拼接的名称

    public CarBO() {
    }

    public CarBO(Integer id, String carName) {
        this.id = id;
        this.carName = carName;
    }
}
