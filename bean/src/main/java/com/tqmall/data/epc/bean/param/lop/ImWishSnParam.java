package com.tqmall.data.epc.bean.param.lop;

import lombok.Data;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/11.
 */
@Data
public class ImWishSnParam {
    private Integer chatObjectId; //聊天对象id
    private List<String> snList; //需求单号集合
}
