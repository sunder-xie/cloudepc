package com.tqmall.data.epc.exterior.dubbo.stall;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.ShopVerityBO;
import com.tqmall.tqmallstall.domain.param.user.VerifyRequest;
import com.tqmall.tqmallstall.domain.result.servant.user.UserDTO;

/**
 * Created by zxg on 16/10/14.
 * 10:24
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface VerifyUserExt {

    //获得认证的相关信息
    Result<UserDTO> getVerifyInfo(Integer userId);

    //申请认证门店
    Boolean register(VerifyRequest verifyRequest);

    //更新认证图片，imageUrl 为有次序的用逗号隔开的数据
    Boolean updateImage(String imageUrl, Integer userId);
}
