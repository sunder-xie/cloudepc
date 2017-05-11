package com.tqmall.data.epc.bean.param.lop;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * Created by huangzhangting on 16/7/31.
 */
@Data
public class WishOrderPayParam extends BaseQueryParam{

    private Integer offerListId;

    //此参数可指定web支付后的跳转页面，使用相对路径
    //如："/Pay/payReturnUrl.html"
    private String webReturnUrl;
}
