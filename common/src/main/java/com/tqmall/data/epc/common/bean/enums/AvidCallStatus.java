package com.tqmall.data.epc.common.bean.enums;

/**
 * Created by huangzhangting on 16/10/26.
 */
public enum AvidCallStatus {
    NO_FOLLOW(0, "未跟进"),
    TEMP_SAVE(1, "暂存"),
    TURN_WISH(2, "生成需求单"),
    SHOP_CANCEL(3, "门店取消");

    AvidCallStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
