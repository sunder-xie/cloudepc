package com.tqmall.data.epc.biz.sys;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huangzhangting on 16/6/21.
 */
public interface UserBiz {
    /**
     * 根据手机号，查询用户
     * @param mobile
     * @return
     */
    UserBO getByMobile(String mobile);

    /**
     * 获取当前用户默认地址
     * @return
     */
    Result<AddressBO> getDefaultAddress();

    /**
     * 获取用户地址列表
     * @return
     */
    Result<List<AddressBO>> getUserAddressList();

    /**
     * 检查门店认证状态
     * @return
     */
    Result<String> checkShopVerifyStatus();


    /**
     * 重新设置 是否认证的状态
     * @return
     */
    void resetVerifyStatus();

    //登录
    Result<UserBO> login(String userName, String verifyCode);
    //登出
    void logout(HttpServletResponse response);
}
