package com.tqmall.data.epc.common.bean.enums;

/**
 * Created by zxg on 16/8/29.
 * 10:54
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public enum OrderStatusEnums {
//    0:未付款 1:已取消 2:已付款 3:已发货 4:已签收 5:已结算
    NOT_PAY(0,"未付款"),
    CANCEL(1,"已取消"),
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

    public static String codeName(Integer code){
        for(OrderStatusEnums status : OrderStatusEnums.values()){
            if(status.getCode().equals(code)){
                return status.getName();
            }
        }
        return ""+code;
    }
}
