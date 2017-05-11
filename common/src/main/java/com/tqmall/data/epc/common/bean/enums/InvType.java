package com.tqmall.data.epc.common.bean.enums;

/**
 * 发票类型枚举
 * Created by huangzhangting on 16/9/3.
 */
public enum InvType {
    NO_INV(0, "不带票"),
    NORMAL_INV(1, "普通发票"),
    ADDED_TAX_INV(2, "增值发票");

    private Integer code;
    private String name;

    InvType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String codeName(Integer code){
        for(InvType type : InvType.values()){
            if(type.getCode().equals(code)){
                return type.getName();
            }
        }
        return ""+code;
    }

}
