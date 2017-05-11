package com.tqmall.data.epc.test.redis;

import com.tqmall.data.epc.common.bean.HttpClientResult;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.utils.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/application-context.xml"})
public class RedisTest {

    @Autowired
    private RedisClientTemplate redisClient;

    @Test
    public void testRedis(){

    }

    //清除缓存，运行请慎重！！！！！！！！
    @Test
    public void testClearRedis() throws ParseException {
        String pattern = "CloudEpc_*";

        String url = "http://localhost:8080/epc/redis/clearRedis";

        //测试环境url
//        String url = "http://114.215.178.133:19341/epc/redis/clearRedis";

        //生产环境
//        String url = "http://epc.tqmall.com/epc/redis/clearRedis";

        String header_key = "Tqmall.epc.ReDis/1";
        long time = System.currentTimeMillis();

        String oneKey = header_key + time;
        oneKey = DigestUtils.md5DigestAsHex(oneKey.getBytes());

        List<NameValuePair> nvps = new ArrayList<>();

        nvps.add(new BasicNameValuePair("time",time+""));
        nvps.add(new BasicNameValuePair("compareKey", oneKey));
        nvps.add(new BasicNameValuePair("pattern", pattern));

        HttpClientResult result = HttpClientUtil.post(url, nvps);

        System.out.println(result.getStatus());
        System.out.println(result.getData());
    }

}
