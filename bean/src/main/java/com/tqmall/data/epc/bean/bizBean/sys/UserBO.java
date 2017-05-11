package com.tqmall.data.epc.bean.bizBean.sys;

import com.tqmall.data.epc.bean.bizBean.shop.AccountBO;
import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;
import com.tqmall.data.epc.bean.bizBean.shop.ShopBO;
import lombok.Data;

import java.util.List;

/**
 * Created by huangzhangting on 16/7/19.
 */
@Data
public class UserBO {
    private String password;
    private String mobile;
    private String hiddenMobile; //处理过后的手机号码，用于页面显示
    private Integer shopId;
    private Integer uid;
    private String realName;
    private Integer accountId;
    private String userTypeCode; //用户类型

    private ShopBO shopBO;
    private List<AccountBO> accountBOs;
    private List<AddressBO> addressBOs;
}
