package com.tqmall.data.epc.client.bean.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lyj on 16/8/29.
 * 开开心心写代码-lyj!
 */
@Data
public class OrderCancelLogDTO implements Serializable{

    //订单取消原因
    private String cancelReason;
}
