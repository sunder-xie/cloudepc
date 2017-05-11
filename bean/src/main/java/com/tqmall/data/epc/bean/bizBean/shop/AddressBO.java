package com.tqmall.data.epc.bean.bizBean.shop;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 16/7/19.
 */
@Data
public class AddressBO {
    private Integer id;
    private String isDeleted;

    private Integer shopId;
    private Integer ownerId;
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private Integer streetId;
    private String address;
    private String zipcode;
    private String contactsName;
    private String mobile;
    private String telephone;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private BigDecimal gdLongitude;
    private BigDecimal gdLatitude;
    private Integer addrType;
    private Integer isDefault;
    private String appSource;
    private Integer modifyTimes;

    private String provinceName;
    private String cityName;
    private String districtName;
    private String streetName;

}
