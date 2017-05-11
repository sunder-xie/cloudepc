package com.tqmall.data.epc.common.bean.enums;

/**
 * Created by huangzhangting on 16/8/3.
 */
public enum PayMessage {

    DES_KEY("1", "R3KdjTyu"),
    DES_IV("2", "20150316"),
    DES_DB_KEY("3", "pZ67jxhn"),
    PAY_LIAN_DESCRY_ERROR("440003", "参数解密失败");

    PayMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
