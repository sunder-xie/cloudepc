package com.tqmall.data.epc.exterior.dubbo.car;

import com.tqmall.athena.domain.result.center.car.CenterCarDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/2/2.
 * 商品中心车型
 */
public interface CenterCarExt {
    List<CenterCarDTO> getCenterCarByPid(Integer pid);

    List<CenterCarDTO> getCenterCarByLevel(Integer level);

    /**
     * 根据车型id查车款集合（即：根据第三级id查询第六级数据）
     * @param modelId
     * @return
     */
    List<CenterCarDTO> getCarListByModelId(Integer modelId);

    CenterCarDTO getCenterCarById(Integer id);
}
