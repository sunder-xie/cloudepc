package com.tqmall.data.epc.biz.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.common.utils.VerifyGenerator;
import com.tqmall.data.epc.exterior.dubbo.base.SmsServiceExt;
import com.tqmall.data.epc.exterior.dubbo.uc.ShopInfoServiceExt;
import com.tqmall.ucenter.object.result.shop.ShopInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/13.
 */
@Slf4j
@Service
public class SmsBizImpl implements SmsBiz{
    @Autowired
    private RedisClientTemplate redisClient;
    @Autowired
    private SmsServiceExt smsServiceExt;
    @Autowired
    private VerifyBiz verifyBiz;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private ShopInfoServiceExt shopInfoServiceExt;


    @Override
    public Result sendVerify(String mobile) {
        if(!EPCUtil.isMobile(mobile)){
            return Result.wrapErrorResult("", "请输入正确的手机号码");
        }

        String action = "send_verify";
        String code = VerifyGenerator.genVerify();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", code);

        Result result = smsServiceExt.sendMsg(mobile, action, dataMap);
        if(result.isSuccess()){
            verifyBiz.addVerifyRecord(mobile, code);
            String key = String.format(RedisKeyBean.VERIFY_CODE_KEY, mobile);
            redisClient.setStringWithTime(key, code, RedisKeyBean.RREDIS_EXP_FIVE_MINUTE);

//            key = String.format(RedisKeyBean.REQUEST_VERIFY_CODE_COUNT_KEY, mobile);
//            String redisStr = redisClient.get(key);
//            if(redisStr==null){
//                redisClient.setStringWithTime(key, "1", RedisKeyBean.RREDIS_EXP_HOURS);
//            }else {
//                redisClient.incr(key);
//            }

            //创建新账号
            getOrCreateShop(ConstantBean.APPLICATION_NAME, mobile);
        }

        return result;
    }
    //创建新账号
    private void getOrCreateShop(final String source, final String mobile) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    ShopInfoDTO shopInfoDTO = shopInfoServiceExt.getOrCreateShopInfoByMobile(source, mobile);
                    if (shopInfoDTO != null) {
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean checkVerifyCodeCount(String mobile) {
        String key = String.format(RedisKeyBean.REQUEST_VERIFY_CODE_COUNT_KEY, mobile);
        String redisStr = redisClient.get(key);
        if(redisStr==null){
            return true;
        }
        int count = Integer.parseInt(redisStr);
        if(count >= ConstantBean.REQUEST_VERIFY_CODE_LIMIT){
            return false;
        }
        return true;
    }


    @Override
    public Result sendNeedAutoSignOrderSms(EpcOrderDO orderDO, int remainDay) {
        if(orderDO==null || remainDay<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("offerListSn", orderDO.getOrderSn());
        dataMap.put("remainTime", remainDay+"天");

        String action = "offer_list_confirm_receipt";

        String mobile = orderDO.getMobile();
        log.info("sendNeedAutoSignOrderSms, mobile:{}, orderSn:{}", mobile, orderDO.getOrderSn());
        return smsServiceExt.sendMsg(mobile, action, dataMap);
    }

    @Override
    public Result sendAutoSignOrderSms(EpcOrderDO orderDO) {
        if(orderDO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("offerListSn", orderDO.getOrderSn());

        String action = "offer_list_auto_confirm_receipt";

        String mobile = orderDO.getMobile();
        log.info("sendAutoSignOrderSms, mobile:{}, orderSn:{}", mobile, orderDO.getOrderSn());
        return smsServiceExt.sendMsg(mobile, action, dataMap);
    }
}
