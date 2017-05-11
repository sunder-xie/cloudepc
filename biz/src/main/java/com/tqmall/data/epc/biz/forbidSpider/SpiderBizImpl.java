package com.tqmall.data.epc.biz.forbidSpider;

import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Created by zxg on 16/7/12.
 * 17:45
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
public class SpiderBizImpl implements SpiderBiz {
    @Autowired
    private RedisClientTemplate redisClient;

    @Override
    public Boolean IncrementIp(String ip) {
        if(StringUtils.isEmpty(ip)){
            return null;
        }
        Boolean result = false;
        String key = String.format(RedisKeyBean.ALL_REQUEST_IP_KEY,ip);
        Integer requestNum = redisClient.lazyGet(key,Integer.class);
        if(requestNum == null){
            requestNum = 0;
        }
        //增加1
        requestNum += 1;

        Integer redisTime = RedisKeyBean.RREDIS_EXP_MINUTE;
        Integer overNum = isNeedIncrementWaiting(ip, requestNum);
        if(overNum > 0){
            //已黑
            result = true;
            redisTime = overNum*ConstantBean.WAITING_EVE_TIME;
        }
        redisClient.lazySet(key, requestNum, redisTime);
        //判断是否过阀值,过了则true
        return result;
    }

    @Override
    public Boolean clearIp(String ip) {
        if(StringUtils.isEmpty(ip)){
            return null;
        }
        String key = String.format(RedisKeyBean.ALL_REQUEST_IP_KEY,ip);
        redisClient.delKey(key);
        return true;
    }

    @Override
    public Integer getOverTimeNum(String ip) {
        if(StringUtils.isEmpty(ip)){
            return null;
        }
        String key = String.format(RedisKeyBean.WAITING_IP_KEY,ip);
        return redisClient.lazyGet(key,Integer.class);
    }

    @Override
    public Integer getCountDownTime(String ip) {
        if(StringUtils.isEmpty(ip)){
            return null;
        }
        String key = String.format(RedisKeyBean.WAITING_IP_KEY,ip);
        return redisClient.getTtl(key).intValue();
    }

    @Override
    public Boolean isInBlackSet(String ip) {
        if(StringUtils.isEmpty(ip)){
            return null;
        }
        return redisClient.isExistInSet(RedisKeyBean.BLACKE_IP_SET_KEY, ip);
    }


    @Override
    public Set<String> getBlackSet() {
        return redisClient.getSet(RedisKeyBean.BLACKE_IP_SET_KEY);
    }

    /*=======private=========*/

    /**
     * ip 短期被封，封的num ＋＋，num 用于记录次数和计算封的时间,
     * @param ip 访问的ip
     * @return 返回阀值数，若没有则返回0
     *  若num大于阀值，则加入黑名单，无限制屏蔽数据访问
     */
    private Integer isNeedIncrementWaiting(String ip,Integer requestNum) {
        String waitingKey = String.format(RedisKeyBean.WAITING_IP_KEY,ip);

        if(requestNum < ConstantBean.REQUEST_MINUTE_LIMIT){
            if(redisClient.getTtl(waitingKey) < 0) {
                //无这个key或者已经过期
                return 0;
            }else {
                return 1;
            }
        }
        //超过阀值次数
        Integer overNum = (requestNum-ConstantBean.REQUEST_MINUTE_LIMIT)/ConstantBean.REQUEST_MINUTE_ADD_LIMIT + 1;

        //次数过大则加入黑名单,再大则没必要加了，因为之前已经加黑名单了
        if(overNum == ConstantBean.IP_WAITING_NUM_LIMIT + 1){
            redisClient.delKey(waitingKey);
            redisClient.addToSet(RedisKeyBean.BLACKE_IP_SET_KEY,ip);
        }else{
            redisClient.lazySet(waitingKey,overNum,overNum*ConstantBean.WAITING_EVE_TIME);
        }

        return overNum;
    }
}
