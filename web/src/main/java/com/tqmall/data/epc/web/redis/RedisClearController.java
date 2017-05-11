package com.tqmall.data.epc.web.redis;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("epc/redis")
@Slf4j
public class RedisClearController {
    private static final String header_key = "Tqmall.epc.ReDis/1";

    @Autowired
    private RedisClientTemplate redisClient;


    //测试方法
    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResponseBody
    public Result test(){
        return Result.wrapSuccessfulResult("test");
    }


    //根据表达式清除缓存
    //例子：CloudEpc_oe_cars_goods_id_* ，也可以是具体值
    @RequestMapping(value = "clearRedis", method = RequestMethod.POST)
    @ResponseBody
    public Result clearRedis(Long time, String compareKey, String pattern){
        if(time==null || compareKey==null || pattern==null){
            return Result.wrapErrorResult("000", "参数不能为空");
        }
        if("".equals(pattern.trim())){
            return Result.wrapErrorResult("000", "参数不能为空");
        }

        try {
            long now = System.currentTimeMillis();

            Long diff = now - time;
            //规定有效期为2个小时
            Long hour = diff / (1000 * 60 * 60);
            if (hour > 2) {
                return Result.wrapErrorResult("001", "超时,相隔时间过长");
            }
            //生成密钥比较
            String oneKey = header_key + time;
            oneKey = DigestUtils.md5DigestAsHex(oneKey.getBytes());
            if (!oneKey.equals(compareKey)) {
                return Result.wrapErrorResult("002", "密钥错误");
            }
            //删除key
            Set<String> keys = redisClient.getKeys(pattern);
            if(keys.isEmpty()){
                return Result.wrapErrorResult("004", "没有相关缓存, pattern: "+pattern);
            }

            return Result.wrapSuccessfulResult(redisClient.delKeys(keys.toArray(new String[keys.size()])));

        }catch (Exception e){
            log.error("clearRedis error, compareKey:"+compareKey+" pattern:"+pattern, e);
            return Result.wrapErrorResult("003", e.getMessage());
        }
    }


    //根据表达式清除缓存
    //例子：CloudEpc_oe_cars_goods_id_* ，也可以是具体值
    @RequestMapping(value = "clearRedisCache")
    @ResponseBody
    public Result clearRedisCache(String pattern){
        if(pattern==null){
            return Result.wrapErrorResult("000", "参数不能为空");
        }
        pattern = pattern.trim();
        if("".equals(pattern)){
            return Result.wrapErrorResult("000", "参数不能为空");
        }

        try {

            //删除key
            Set<String> keys = redisClient.getKeys(pattern);
            if(keys.isEmpty()){
                return Result.wrapErrorResult("001", "没有相关缓存, pattern: "+pattern);
            }

            //验证key
            List<String> delKeyList = new ArrayList<>();
            for(String key : keys){
                if(!isUnFreshKey(key)){
                    delKeyList.add(key);
                }
            }

            return Result.wrapSuccessfulResult(redisClient.delKeys(delKeyList.toArray(new String[delKeyList.size()])));

        }catch (Exception e){
            log.error(e.getMessage(),e);
            return Result.wrapErrorResult("003",e.getMessage());
        }
    }
    //检验是否不可刷缓存的key
    private boolean isUnFreshKey(String checkKey){
        for(String key : RedisKeyBean.UN_FRESH_KEY_SET) {
            String keyReg = key.replaceAll("%s", ".+").replaceAll("%d", ".+");
            Pattern pattern = Pattern.compile(keyReg);
            Matcher matcher = pattern.matcher(checkKey);
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }

}
