package com.tqmall.data.epc.exterior.dubbo.car;

import com.tqmall.athena.domain.result.carcategory.CarCategoryDTO;
import com.tqmall.athena.domain.result.carcategory.CarListDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/1/27.
 * 线上车型
 */
public interface CarCategoryExt {
    List<CarListDTO> categoryCarInfo(Integer pid);

    CarCategoryDTO getCarById(Integer id);

    List<CarCategoryDTO> getByLevel(Integer level);
}
