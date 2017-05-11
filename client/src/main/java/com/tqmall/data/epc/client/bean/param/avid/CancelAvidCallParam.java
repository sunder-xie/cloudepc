package com.tqmall.data.epc.client.bean.param.avid;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class CancelAvidCallParam implements Serializable{
    private String operator; //操作人
    private Integer id; //急呼数据主键id
    private String cancelReason; //取消原因
}
