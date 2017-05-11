package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/8.
 */
public interface AddressServiceExt {

    List<AddressBO> getAddressByTypeAndUserId(String appName, Integer userId, Integer userType);
}
