package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.ucenter.object.result.account.AccountDTO;
import com.tqmall.ucenter.service.account.RpcAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangzhangting on 16/7/12.
 */
@Service
@Slf4j
public class AccountServiceExtImpl implements AccountServiceExt{
    @Autowired
    private RpcAccountService rpcAccountService;

    @Override
    public AccountDTO getAccountByMobile(String appName, String mobile) {
        log.info("get account, appName:{}, mobile:{}", appName, mobile);

        Result<AccountDTO> result = rpcAccountService.getAccountByMobile(appName, mobile);
        if(result.isSuccess()){
            return result.getData();
        }

        log.info("get account, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

}
