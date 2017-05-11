package com.tqmall.data.epc.bean.bizBean.shop;

import lombok.Data;

/**
 * Created by huangzhangting on 16/7/19.
 */
@Data
public class AccountBO {
    private Integer id;
    private String isDeleted;

    private Integer shopId;
    private Integer ownerId;
    private String realName;
    private String mobile;
    private String jobTitle;
    private String email;
    private String wechat;

    private String telephone;
    private Integer accountStatus;
    private String accountName;
    private String password;
    private String salt;
    private String appSource;
    private String memo;
}
