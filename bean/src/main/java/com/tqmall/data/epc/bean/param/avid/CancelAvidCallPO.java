package com.tqmall.data.epc.bean.param.avid;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class CancelAvidCallPO implements Serializable{
    private Integer id; //急呼数据主键id
    private String cancelReason; //取消原因
    private String operator; //操作人

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", cancelReason='" + cancelReason + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
