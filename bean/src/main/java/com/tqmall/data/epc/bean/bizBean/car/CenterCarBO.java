package com.tqmall.data.epc.bean.bizBean.car;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/2/2.
 */
@Data
public class CenterCarBO {
    private Integer id;

    private String name;

    private String firstWord;

    private Integer pid;

    private String company;

    private String importInfo;

    private String logo;

    private String year;

    private String power;

    private String model;

    private String series;

    private String brand;

    private Object customAttr; //自定义属性

}
