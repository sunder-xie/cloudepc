package com.tqmall.data.epc.bean.entity.order;

import lombok.Data;

import java.util.Date;

@Data
public class EpcOrderStatusLogDO {
    private Integer id;

    //操作人
    private String operatorName;

    //操作时间
    private Date operateTime;

    //操作人来源 0：系统操作 1：门店 2：供应商 3：ERP
    private Integer operatorSource;

    //订单id
    private Integer orderId;

    //订单编号
    private String orderSn;

    //订单状态枚举
    private Integer orderStatus;

    //订单状态描述
    private String orderStatusDesc;

    //日志内容
    private String logContent;

}