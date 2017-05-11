package com.tqmall.data.epc.exterior.dubbo.holy;

import com.tqmall.core.common.entity.Result;
import com.tqmall.holy.provider.entity.VerifyInfoDTO;
import com.tqmall.holy.provider.service.RpcVerifyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zxg on 16/10/20.
 * 15:37
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
@Slf4j
public class VerifyMessageExtImpl implements VerifyMessageExt {

    @Autowired
    private RpcVerifyInfoService verifyInfoService;

    @Override
    public Result<VerifyInfoDTO> getVerifyInfoByMobile(String mobile) {
        log.info("getVerifyInfoByMobile,mobile:{}", mobile);

        try {
            Result<VerifyInfoDTO> result = verifyInfoService.getVerifyInfoByMobile(mobile);
            return result;
        } catch (Exception e) {
            log.error("getVerifyInfoByMobile,error:",e);
            return null;
        }
    }
}
