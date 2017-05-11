package com.tqmall.data.epc.exterior.dubbo.car;

import com.tqmall.athena.domain.result.center.car.CenterCarCatDTO;

import java.util.List;

/**
 * Created by zxg on 16/2/3.
 * 15:11
 * 商品中心的车型对应的商品分类
 */
public interface CenterCarCatExt {

//    获得常用车车型下的所有商品分类数据
    List<CenterCarCatDTO> getListByCarIdC(Integer carId);
}
