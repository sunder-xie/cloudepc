package com.tqmall.data.epc.bean.bizBean.order.fee;

import lombok.Data;

/**
 * Created by huangzhangting on 16/7/31.
 */
@Data
public class OrderOLPaymentBO {
    private Integer id;          // 编号
    private String code;        // 关键字
    private String title;       // 标题
    private String url;         // 跳转链接
    private String description; // 描述
    private boolean isDefault;  // 是否默认选择
    private boolean selectable; // 是否可选择
    private String alertInfo;   // 弹框提示
}
