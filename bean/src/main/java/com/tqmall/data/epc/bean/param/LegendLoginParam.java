package com.tqmall.data.epc.bean.param;

import lombok.Data;

/**
 * 云修系统登录参数
 * Created by huangzhangting on 16/8/10.
 */
@Data
public class LegendLoginParam {
    /*
    //退换货sn
    private String exchangeOrderSn;

    private Integer payType;

    private Integer purchaseId;

    //云修采购清单：OPT_YUNXIU_PURCHASE/云修退换货：OPT_YUNXIU_THHH
    private String optType;
    */

    private String mobile;
    private Integer cityId;//城市站
}
