package com.tqmall.data.epc.bean.param.order.pay;

import lombok.Data;

import java.util.Date;

/**
 * Created by huangzhangting on 16/9/2.
 */
@Data
public class UnionPayFormParam extends AliPayFormParam{
    //下单时间
    private Date gmtCreate;

    //签约协议号(貌似没用到)
    private String noAgree;
    //银行卡 卡号
    private String cardNo;
    //持卡人
    private String acctName;
    //身份证
    private String  idNo;
}
