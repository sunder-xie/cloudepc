package com.tqmall.data.epc.bean.entity.order;

import lombok.Data;

import java.util.Date;

@Data
public class EpcOrderCancelLogDO {
    private Integer id;

    private String operatorName;

    private Date operateTime;

    private Integer orderId;

    private String orderSn;

    private String cancelReason;

}