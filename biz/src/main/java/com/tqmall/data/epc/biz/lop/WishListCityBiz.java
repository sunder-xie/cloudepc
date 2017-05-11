package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.Result;

/**
 * Created by huangzhangting on 16/8/11.
 */
public interface WishListCityBiz {
    /**
     * 校验城市站
     * @param cityId
     * @return
     */
    Result checkCity(Integer cityId);

    String getCityNames();
}
