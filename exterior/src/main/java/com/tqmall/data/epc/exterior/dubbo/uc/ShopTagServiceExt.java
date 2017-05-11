package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.ucenter.object.result.shoptag.ShopTagDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/9/2.
 */
public interface ShopTagServiceExt {
    /**
     * 根据门店id，查询门店标签
     * @param appName
     * @param shopId
     * @return
     */
    List<ShopTagDTO> getTagsByShopId(String appName, Integer shopId);
}
