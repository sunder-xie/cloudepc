package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.util.Date;

/**
 * 需求单竞价信息封装对象
 * Created by huangzhangting on 16/8/3.
 */
@Data
public class CompetitorInfoBO {
    private Integer wishListId;
    private Date createOfferTime;
    private Date wishListGmtCreate;
    private Integer sellerId;
    private String sellerName;
    private String sellerTel;
//    private String status;
    private String statusStr;
}
