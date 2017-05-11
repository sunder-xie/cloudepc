package com.tqmall.data.epc.bean.param;

import com.tqmall.data.epc.common.bean.ConstantBean;
import lombok.Data;

/**
 * 基础查询参数对象（参考自web）
 */
@Data
public class BaseQueryParam {

    private final String version = "2.0";

    private String deviceId = "data_epc";

    private String source = ConstantBean.APPLICATION_NAME;

    private String requestSource = ConstantBean.APPLICATION_NAME;

    private Integer limit = 20;

    private Integer start = 0;

    private int uid;

    private Integer cityId;

    private String token;

    private Integer sellerId = 1;

    private String sessionId;

    private String sign;

    private Integer accountId;

}
