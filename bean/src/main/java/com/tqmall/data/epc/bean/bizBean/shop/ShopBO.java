package com.tqmall.data.epc.bean.bizBean.shop;

import lombok.Data;

/**
 * Created by huangzhangting on 16/7/19.
 */
@Data
public class ShopBO {
    private Integer id;
    private String isDeleted;

    //门店名称
    private String companyName;
    //门店营业执照名称
    private String companyFormalName;
    //电商系统客户编码，主要在金蝶系统中使用
    private String clientNum;
    //电商系统原有ID
    private Integer userId;
    //认证状态:-1认证失败，0未认证，1认证中，2认证通过
    private Integer verifyStatus;
    //客户交易类型:1表示B用户，2表示C用户，3表示KA客户
    private Integer shopType;
    //门店状态:-1冻结，0正常
    private Integer shopStatus;
    //备注
    private String memo;
    //会员等级
    private Integer userRank;
    //等级积分值
    private Integer rankPoints;
    //门店归属小淘汽的组织id
    private Integer ownerId;
    private String appSource;
}
