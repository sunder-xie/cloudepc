package com.tqmall.data.epc.bean.bizBean.pay;

import lombok.Data;

/**
 * Created by huangzhangting on 16/8/3.
 */
@Data
public class UserBankCardBO {
    private String noAgree;//签约协议号

    private String cardNo;//银行卡号

    private String acctName;//银行账户姓名

    private String idNo;//证件号码

    private String bankCode;//银行所属编号

    private String bankName;//银行名称

    private Integer cardType;//卡的类型

    private String bankLogo;//银行卡logo

    private String bankLogoWeb;//web银行卡logo
}
