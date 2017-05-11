package com.tqmall.data.epc.bean.bizBean.shop;

import lombok.Data;

/**
 * Created by zxg on 16/10/14.
 * 10:27
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Data
public class ShopVerityBO {
    //门头名称，就是他们店挂在外边的名称
    private String userTitle;
    //营业执照
    private String businessLicence;
    //联系人
    private String contact;
    //省份id
    private Integer province;
    //城市id
    private Integer city;
    //区县id
    private Integer district;
    //街道id
    private Integer street;
    //详细地址
    private String address;

    /**
     * 销售电话
     */
    private String saleMobile;
    //图片，逗号隔开，调用认证接口用
    private String imgs;
    //用户 uid，调用认证接口用
    private Integer uid;

    //认证失败的原因，用于失败时的展示
    private String verifyFeedback;
}
