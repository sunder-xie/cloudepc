package com.tqmall.data.epc.bean.entity.sys;

import lombok.Data;

import java.util.Date;

@Data
public class EpcUserPvRecordDO {
    private Integer id;

    private String isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer pageUriId;

    private String userMobile;

}