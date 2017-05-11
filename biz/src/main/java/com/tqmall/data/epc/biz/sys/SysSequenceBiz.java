package com.tqmall.data.epc.biz.sys;

/**
 * Created by huangzhangting on 16/8/27.
 */
public interface SysSequenceBiz {
    /**
     * 订单编号生成
     *
     * Q + 年的后两位＋日期的四位 ＋ 四位随机数 ＋ 四位序列号
     * 序列号从0001开始
     * 比如：Q15082735890001
     *
     * @return
     */
    String genOrderNo();

}
