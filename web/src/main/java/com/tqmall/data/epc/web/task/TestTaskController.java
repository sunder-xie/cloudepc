package com.tqmall.data.epc.web.task;

import com.tqmall.data.epc.common.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zxg on 15/11/18.
 */

@RestController
@RequestMapping("/testTask")
public class TestTaskController {


    @Scheduled(cron = "0 15 * * * ?")
//    @RequestMapping(value = "/testA", method = RequestMethod.GET)
    public void test(){
        String result = DateUtil.dateToString(new Date(),DateUtil.yyyy_MM_dd_HH_mm_ss);
        System.out.println("==========test task========"+result);
    }
}
