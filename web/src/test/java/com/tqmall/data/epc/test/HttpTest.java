package com.tqmall.data.epc.test;

import com.tqmall.data.epc.common.bean.HttpClientResult;
import com.tqmall.data.epc.common.utils.HttpClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huangzhangting on 16/1/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/application-context.xml"})
public class HttpTest {
    @Test
    public void test(){
        String url = "http://search.yipei360.com/elasticsearch/dandelion/shop/list?cityId=383&size=100000&showService=0";
        HttpClientResult result = HttpClientUtil.get(url);
        System.out.println(result);
    }
}
