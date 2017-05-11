package com.tqmall.data.epc.common.bean.enums;

/**
 * Created by zxg on 16/8/30.
 * 14:06
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public enum SourceEnums {
    //1：门店 2：供应商 3：ERP
    STORES(1,"门店"),
    PROVIDERS(2,"供应商"),
    ERP(3,"ERP");


    private Integer code;

    private String name;

    SourceEnums(Integer code, String name){
        this.code = code;
        this.name = name;
    }
    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Boolean isInEnums(Integer code){
        for(SourceEnums status : SourceEnums.values()){
            if(status.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }
}
