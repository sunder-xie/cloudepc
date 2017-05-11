package com.tqmall.data.epc.bean.bizBean.pay;

import lombok.Data;

/**
 * Created by huangzhangting on 16/8/3.
 */
@Data
public class BankCardBO {
    private String bankCode;

    private String bankName;

    private Integer cardType;

    private String bankLogo;

    private String bankLogoWeb;

    private boolean isSupported;
}
