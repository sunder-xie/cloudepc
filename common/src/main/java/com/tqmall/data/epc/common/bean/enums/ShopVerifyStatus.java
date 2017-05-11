package com.tqmall.data.epc.common.bean.enums;

/**
 * Created by huangzhangting on 16/8/8.
 */
public enum ShopVerifyStatus {
    INIT_STATUS(0, "未认证", "抱歉，您尚未成为汽配管家会员，请先认证"),
    ING_STATUS(1, "认证中", "抱歉，您的会员申请正在审核中，暂时无法使用该功能"),
    SUCCESS_STATUS(2, "认证通过", ""),
    FAILED_STATUS(-1, "认证失败", "抱歉，您的会员申请未通过审核，暂时无法使用该功能");

    private Integer code;
    private String desc;
    private String message;

    ShopVerifyStatus(Integer code, String desc, String message) {
        this.code = code;
        this.desc = desc;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getMessage() {
        return message;
    }

    public static String codeMsg(Integer code){
        for(ShopVerifyStatus status : ShopVerifyStatus.values()){
            if(status.getCode().equals(code)){
                return status.getMessage();
            }
        }
        return INIT_STATUS.getMessage();
    }
}
