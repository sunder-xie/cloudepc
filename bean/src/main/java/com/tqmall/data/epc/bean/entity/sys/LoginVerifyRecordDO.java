package com.tqmall.data.epc.bean.entity.sys;

import lombok.Data;

import java.util.Date;

@Data
public class LoginVerifyRecordDO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private String verifyMobile;

    private String verifyCode;

    private Byte verifyStatus;

    private Date verifyTime;

}