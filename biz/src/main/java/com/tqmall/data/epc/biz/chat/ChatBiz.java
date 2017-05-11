package com.tqmall.data.epc.biz.chat;

/**
 * Created by zxg on 16/9/9.
 * 10:40 聊天的biz 类
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface ChatBiz {
    /*====筛选购买订单==========*/
    //新订单通知，门店(shop_id)通知供应商(org_id)
    //同时通知 客服
    void newOrderNotify(Integer shopId,String shopName,Integer orgId,String orgName,String orgTel,String orderSn,String priceAmount);

    //改价 im 通知，供应商(org_id)通知门店(shop_id)
    void changePriceNotify(Integer shopId,Integer orgId,String orderSn);

    //发货im通知，供应商(org_id)通知门店(shop_id)
    void haveShippingNotify(Integer shopId,Integer orgId,String orderSn);

    //客户签收im通知，门店(shop_id)通知供应商(org_id)
    void haveReceiveNotify(Integer shopId,Integer orgId,String orderSn);

    /*======需求单===========*/
    //新需求单通知,
    // ${cityName}城市的门店${companyName}发布了关于${carName}车型的需求单
    void newWishOrderNotify(String companyName,String carName,String cityName);
}
