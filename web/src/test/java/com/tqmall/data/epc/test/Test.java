package com.tqmall.data.epc.test;

import com.tqmall.data.epc.exterior.dubbo.stall.StallGoodsExt;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huangzhangting on 16/7/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/application-context.xml"})
public class Test {
    @Autowired
    private StallGoodsExt stallGoodsExt;

    @org.junit.Test
    public void test(){

        System.out.println(123123);


        Integer i = 1;
        System.out.println(i.equals(null));
    }

}
