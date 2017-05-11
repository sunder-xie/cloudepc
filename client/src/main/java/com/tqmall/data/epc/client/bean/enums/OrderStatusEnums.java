package com.tqmall.data.epc.client.bean.enums;

/**
 * Created by zxg on 16/8/29.
 * 10:54
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public enum OrderStatusEnums {
    NOT_PAY(0,"未付款"),
    CANCLE(1,"已取消"),
    HAVE_PAY(2,"已付款"),
    HAVE_SHIPMENTS(3,"已发货"),
    HAVE_RECEIVE(4,"已签收"),
    HAVE_SETTLE(5,"已结算");

    private Integer code;

    private String name;

    OrderStatusEnums(Integer code, String name){
        this.code = code;
        this.name = name;
    }
    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
