package com.tqmall.data.epc.biz.verifyShop;

import com.tqmall.data.epc.bean.bizBean.shop.ShopVerityBO;

/**
 * Created by zxg on 16/10/14.
 * 17:35
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface VerifyShopBiz {

    // 获得预处理的认证数据，用于 还未认证过的门店
    ShopVerityBO getPreVerifyInfo(Integer userId);

    // 获得在处理或处理失败的认证数据，用于正在认证/认证失败的门店
    ShopVerityBO getVerifyInfo(String mobile);

    //申请认证门店
    Boolean register(ShopVerityBO shopVerityBO);

    //更新认证图片，imageUrl 为有次序的用逗号隔开的数据
    Boolean updateImage(String imageUrl, Integer userId);
}
