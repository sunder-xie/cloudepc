package com.tqmall.data.epc.biz.forbidSpider;

import java.util.Set;

/**
 * Created by zxg on 16/7/12.
 * 17:06
 * no bug,以后改代码的哥们，祝你好运~！！
 * 防爬虫的biz类
 */
public interface SpiderBiz {

    /**
     * ip 访问量自增，
     * @param ip:访问的ip
     * @return
     * 若大于阀值，则return true
     * 插入成功，没过阀值，return false
     */
    Boolean IncrementIp(String ip);

    //清空此ip的单位时间请求量
    Boolean clearIp(String ip);

    //超过阀值次数,获得 其次数
    Integer getOverTimeNum(String ip);

    //封锁的倒计时秒数
    Integer getCountDownTime(String ip);


    //是否在黑名单中
    Boolean isInBlackSet(String ip);

    //获得黑名单ip列表
    Set<String> getBlackSet();


}
