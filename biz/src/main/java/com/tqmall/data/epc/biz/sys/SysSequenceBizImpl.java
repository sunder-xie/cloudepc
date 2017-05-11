package com.tqmall.data.epc.biz.sys;

import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.DateUtil;
import com.tqmall.data.epc.common.utils.EPCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huangzhangting on 16/8/27.
 */
@Service
@Slf4j
public class SysSequenceBizImpl implements SysSequenceBiz{
    private static final Lock ORDER_NO_LOCK = new ReentrantLock(true);

    @Autowired
    private RedisClientTemplate redisClient;

    //从redis获取序列值
    private int getSeqFromRedis(String key, int defaultVal, int expire){
        String seqStr = redisClient.get(key);
        if(seqStr == null){
            if(expire < 1){
                redisClient.set(key, defaultVal+"");
            }else {
                redisClient.setStringWithTime(key, defaultVal + "", expire);
            }

            return defaultVal;
        }
        return redisClient.incr(key).intValue();
    }

    @Override
    public String genOrderNo() {
        String date = DateUtil.dateToString(new Date(), DateUtil.yyMMdd);
        String key = String.format(RedisKeyBean.ORDER_NO_SEQ_KEY, date);
        String no = "Q" + date;
        ORDER_NO_LOCK.lock();
        try {
            int seq = getSeqFromRedis(key, 1, RedisKeyBean.RREDIS_EXP_DAY)%1000;
            no = no + EPCUtil.getRandomNum(4) + EPCUtil.supplementNum(seq, 4);
            log.info("genOrderNo success, no:{}", no);
        }catch (Exception e){
            no = no + "9999" + EPCUtil.getRandomNum(4);
            log.error("genOrderNo error, no:"+no, e);
        }finally {
            ORDER_NO_LOCK.unlock();
        }
        return no;
    }
}
