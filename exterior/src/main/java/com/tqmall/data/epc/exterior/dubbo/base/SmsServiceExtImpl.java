package com.tqmall.data.epc.exterior.dubbo.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.tqmallstall.domain.param.sms.SmsParam;
import com.tqmall.tqmallstall.service.sms.AppSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by huangzhangting on 16/7/12.
 */
@Service
@Slf4j
public class SmsServiceExtImpl implements SmsServiceExt{
    @Autowired
    private AppSmsService stallSmsService;


    @Override
    public Result sendMsg(String mobiles, String action, Map<String, Object> dataMap) {
        if(StringUtils.isEmpty(mobiles)){
            return Result.wrapErrorResult("", "手机号码不能为空");
        }
        if(StringUtils.isEmpty(action)){
            return Result.wrapErrorResult("", "短信模板key，不能为空");
        }

        SmsParam smsParam = new SmsParam();
        smsParam.setSource("epc");
        smsParam.setAction(action);
        smsParam.setMobile(mobiles);

        if(!CollectionUtils.isEmpty(dataMap)){
            smsParam.setData(JsonUtil.objectToJson(dataMap));
        }

        Result result = stallSmsService.sendSms(smsParam);
        if(result.isSuccess()){
            return result;
        }

        log.info("短信发送失败, result:{}", result);
        return Result.wrapErrorResult("", "非常抱歉，短信发送失败");
    }

}
