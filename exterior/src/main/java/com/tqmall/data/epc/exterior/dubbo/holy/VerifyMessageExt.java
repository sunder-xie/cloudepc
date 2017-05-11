package com.tqmall.data.epc.exterior.dubbo.holy;

import com.tqmall.core.common.entity.Result;
import com.tqmall.holy.provider.entity.VerifyInfoDTO;

/**
 * Created by zxg on 16/10/20.
 * 15:37
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface VerifyMessageExt {
    //根据手机号获得 已经申请的认证信息
    Result<VerifyInfoDTO> getVerifyInfoByMobile(String mobile);
}
