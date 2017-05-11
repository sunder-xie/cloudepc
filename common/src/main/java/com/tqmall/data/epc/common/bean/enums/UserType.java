package com.tqmall.data.epc.common.bean.enums;

/**
 * 用户类型，参考自档口
 * Created by huangzhangting on 16/8/8.
 */
public enum UserType {
    B_USER("B_USER", "企业用户", "B"),
    C_USER("C_USER", "个人用户", "C"),
    K_USER("K_USER", "KA客户", "K"),
    YUN_XIU("YUN_XIU", "云修客户", "B"),

    K_TDHY("K_TDHY", "KA用户价格标签", "K_TDHY"),
    K_LHSHWL("K_LHSHWL", "KA用户价格标签", "K_LHSHWL"),
    ZXC_TQDQ("ZXC_TQDQ", "专项仓客户价格标签","ZXC_TQDQ");

    private String code;

    private String desc;

    private String tag;

    UserType(String code, String desc, String tag) {
        this.code = code;
        this.desc = desc;
        this.tag = tag;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getTag() {
        return tag;
    }

    public static UserType valueOf(Byte code) {
        for (UserType userType : UserType.values()) {
            if (userType.getCode().equals(String.valueOf(code))) {
                return userType;
            }
        }
        return null;
    }
}
