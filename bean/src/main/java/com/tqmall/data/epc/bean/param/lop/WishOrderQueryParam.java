package com.tqmall.data.epc.bean.param.lop;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * 订单查询参数对象
 * Created by huangzhangting on 16/7/31.
 */
@Data
public class WishOrderQueryParam extends BaseQueryParam {
    private Integer p; //页码，从1开始

    private String status; //订单状态,多个状态,以逗号隔开

    private String offerListSn; //订单编号

    private String keyword;
}
