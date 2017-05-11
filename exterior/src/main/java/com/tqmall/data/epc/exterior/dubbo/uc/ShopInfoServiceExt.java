package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.ucenter.object.result.shop.ShopDTO;
import com.tqmall.ucenter.object.result.shop.ShopInfoDTO;

/**
 * Created by huangzhangting on 16/7/19.
 */
public interface ShopInfoServiceExt {
    /**
     * 根据门店id，查询门店详细信息
     * @param appName
     * @param shopId
     * @return
     */
    ShopInfoDTO getShopInfoByShopId(String appName, Integer shopId);

    /**
     * 如果帐号不存在则创建帐号
     * @param appName
     * @param mobile
     * @return
     */
    ShopInfoDTO getOrCreateShopInfoByMobile(String appName, String mobile);

    /**
     * 根据门店id，查询门店
     * @param appName
     * @param shopId
     * @return
     */
    ShopDTO getShopByShopId(String appName, Integer shopId);
}
