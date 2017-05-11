package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.util.Date;

@Data
public class OfferListStatusRecordBO {

    private Integer id;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer creator;

    private Integer modifier;

    private String isDeleted;

    private Integer wishListId;

    private Integer offerListId;

    private String operateKey;

    private String operateName;

    private String log;

    private String attributes;

}
