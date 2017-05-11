package com.tqmall.data.epc.web.task;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import com.tqmall.data.epc.biz.base.SmsBiz;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 每小时执行一次
 * AutoConfirmReceive, 自动确认收货
 * Created by lyj on 16/9/6.
 * 开开心心写代码-lyj!
 */
@Slf4j
@RestController
@RequestMapping("arcTask")
public class AcrTaskController {

    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private SmsBiz smsBiz;


    /**
     * 1、确认收货短信提醒
     * 2、自动确认收货
     * 3、每小时零一秒 执行一次
     */
    @Scheduled(cron = "1 0 */1 * * ?")
    @RequestMapping("test")
    public void doBusiness() {

        //供应商确认发货后第10天，即离自动确认收货还有5天，发短信提醒
        doSendConfirmReceiveSms(5);

        //供应商确认发货后第14天，即离自动确认收货还有1天，发短信提醒
        doSendConfirmReceiveSms(1);

        //自动确认收货，并发送短信
        doAutoConfirmReceive();

    }

    /**
     * 确认收货短信提醒，第10天、第14天提醒
     */
    private void doSendConfirmReceiveSms(int remainDay) {
        if (!isInSendSmsTime()) {
            return;
        }

        //获取时间节点
        Map<String, String> timeParam = getStartEndTime(remainDay);

        //获取, 当前时间需要发送短信的订单
        Result<List<EpcOrderDO>> result =
                orderBiz.getNeedAutoSignOrder(timeParam.get("startTime"), timeParam.get("endTime"));

        if (result.isSuccess()) {
            log.info("select order, remain {} day, auto sign sms tip", remainDay);
            for(EpcOrderDO orderDO : result.getData()){
                smsBiz.sendNeedAutoSignOrderSms(orderDO, remainDay);
            }
        }

    }

    /**
     * 定时自动确认收货
     */
    private void doAutoConfirmReceive() {
        //获取时间节点
        Map<String, String> timeParam = getAutoSignStartEndTime();

        //获取, 当前时间需要自动确认收货的订单
        Result<List<EpcOrderDO>> result =
                orderBiz.getNeedAutoSignOrder(timeParam.get("startTime"), timeParam.get("endTime"));

        if (result.isSuccess()) {
            log.info("select order, auto sign");
            for(EpcOrderDO orderDO : result.getData()){
                Result receiveResult = orderBiz.autoConfirmReceive(orderDO.getOrderSn());
                if(receiveResult.isSuccess()){
                    smsBiz.sendAutoSignOrderSms(orderDO);
                }
            }
        }

    }

    /**
     * 检查是否在发短信时间内(每天22点-8点，不发短信)
     *
     * 短息提前提醒时使用，自动签收时，不走这个判断
     *
     * @return 返回true可以发短信
     */
    private boolean isInSendSmsTime() {
        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);
        return !(h >= 22 || h < 8);
    }

    /**
     * 组装查询开始时间和结束时间
     * 晚上21点，要发送，21点到24点订单的提醒
     * 早上8点，要发送，0点到9点订单的提醒
     * 其他时间段，从当前小时的0分0秒，到下一个小时的0分0秒（因为定时任务每小时执行一次）
     *
     * @param day 距离确认收货还剩的天数
     * @return map包含startTime、endTime 2个参数
     */
    private Map<String, String> getStartEndTime(int day) {
        Map<String, String> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);//分钟清零
        calendar.set(Calendar.SECOND, 0);//秒数清零

        String df = DateUtil.yyyy_MM_dd_HH_mm_ss;
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if (h == 21) {
            calendar.set(Calendar.HOUR_OF_DAY, 21);
            map.put("startTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            map.put("endTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));

        } else if (h == 8) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            map.put("startTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            map.put("endTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));

        } else {
            map.put("startTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));
            calendar.set(Calendar.HOUR_OF_DAY, h + 1);
            map.put("endTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));

        }

        return map;
    }

    /**
     * 组装自动签收的查询开始时间和结束时间
     * 当前小时的0分0秒，到之前5小时
     *
     * 每小时执行一次，如果系统发生过异常，5小时前未签收数据也能签收
     *
     * @return map包含startTime、endTime 2个参数
     */
    private Map<String, String> getAutoSignStartEndTime() {
        Map<String, String> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        map.put("endTime", DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy_MM_dd_HH_mm_ss));
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        map.put("startTime", DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy_MM_dd_HH_mm_ss));
        return map;
    }


}
