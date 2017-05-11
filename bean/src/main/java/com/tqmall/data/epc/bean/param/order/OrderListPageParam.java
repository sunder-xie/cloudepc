package com.tqmall.data.epc.bean.param.order;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * Created by zxg on 16/8/29.
 * 09:41 订单列表分页查询
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Data
public class OrderListPageParam{
    private Integer searchP; //页码，从1开始

    private String searchStatus; //订单状态,多个状态,以逗号隔开

    //对内系统，无需传递，由 session 获得
    private Integer shopId;
}
