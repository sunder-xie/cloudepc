package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.ucenter.object.result.account.AccountDTO;

/**
 * Created by huangzhangting on 16/7/12.
 */
public interface AccountServiceExt {
    /**
     * 根据手机号，查询账号信息
     * @param appName
     * @param mobile
     * @return
     */
    AccountDTO getAccountByMobile(String appName, String mobile);

}
