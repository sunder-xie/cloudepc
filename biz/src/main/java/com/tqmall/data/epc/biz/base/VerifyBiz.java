package com.tqmall.data.epc.biz.base;

/**
 * Created by huangzhangting on 16/7/13.
 */
public interface VerifyBiz {

    /**
     * 插入生成的验证码
     * @param mobile
     * @param code
     * @return
     */
    void addVerifyRecord(String mobile, String code);

    /**
     * 根据手机号，校验验证码
     * @param mobile
     * @param code
     * @return
     */
    boolean verifying(String mobile, String code);

}
