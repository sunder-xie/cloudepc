package com.tqmall.data.epc.biz.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;

/**
 * Created by huangzhangting on 16/7/13.
 */
public interface SmsBiz {
    /**
     * 发送验证码
     * @param mobile
     */
    Result sendVerify(String mobile);

    /**
     * 校验验证码请求次数
     * @param mobile
     * @return
     */
    boolean checkVerifyCodeCount(String mobile);

    /**
     * 筛选订单自动签收短息提醒
     * @param orderDO
     * @param remainDay
     * @return
     */
    Result sendNeedAutoSignOrderSms(EpcOrderDO orderDO, int remainDay);

    /**
     * 自动签收完成，短信提醒
     * @param orderDO
     * @return
     */
    Result sendAutoSignOrderSms(EpcOrderDO orderDO);
}
