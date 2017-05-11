package com.tqmall.data.epc.bean.bizBean.base;

import lombok.Data;

/**
 * Created by huangzhangting on 16/8/2.
 */
@Data
public class RegionBO {

    /**
     * 地区ID
     */
    private Integer id;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 层级
     */
    private Integer regionType;

    /**
     * 首字母
     */
    private String firstLetter;

}
