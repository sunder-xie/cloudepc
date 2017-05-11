package com.tqmall.data.epc.bean.param.lop;

import lombok.Data;

/**
 * Created by huangzhangting on 16/7/31.
 */
@Data
public class WishOrderUnionPayParam extends WishOrderPayParam{

    private String noAgree;     //签约协议号

    private String cardNo;      //银行卡号

    private String acctName;    //银行帐号姓名

    private String idNo;        //证件号码
}
