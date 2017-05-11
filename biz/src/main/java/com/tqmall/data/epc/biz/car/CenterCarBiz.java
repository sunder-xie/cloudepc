package com.tqmall.data.epc.biz.car;

import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;

import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/2/2.
 */
public interface CenterCarBiz {

    /**
     * 车型筛选页面，根据车型id查询车款
     * @param modelId
     * @return
     */
    List<CenterCarBO> getCarListForFilter(Integer modelId);

    /**
     * 车型筛选页面，根据车系查询车型
     * 如果只有一个车型，直接查出车款
     * @param seriesId
     * @return
     */
    List<CenterCarBO> getModelForFilter(Integer seriesId);

    /**
     * 车型筛选页面，查询品牌
     * @return
     */
    Map<String, List<CenterCarBO>> getBrandForFilter();

    /**
     * 查询全部品牌
     * @return
     */
    List<CenterCarBO> getCarBrands();
}
