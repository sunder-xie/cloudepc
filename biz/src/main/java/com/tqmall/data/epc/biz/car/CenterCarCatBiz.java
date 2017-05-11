package com.tqmall.data.epc.biz.car;

import com.tqmall.data.epc.bean.bizBean.car.CenterCarCatBO;

import java.util.List;
import java.util.Map;

/**
 * Created by zxg on 16/2/3.
 * 15:16
 */
public interface CenterCarCatBiz {

    /**
     * 根据线上车型id获得三级分类
     * {一级分类：二级分类：list<三级分类>}
     * @param carId 线上车型id
     * @return 分类map
     */
    Map<CenterCarCatBO,Map<CenterCarCatBO,List<CenterCarCatBO>>> getCatByCarId(Integer carId);
}
