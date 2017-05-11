package com.tqmall.data.epc.biz.shiro;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.base.CommonBiz;
import com.tqmall.data.epc.biz.sys.UserBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;


/**
 * Created by huangzhangting on 15/9/11.
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private CommonBiz commonBiz;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String mobile = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(new HashSet<String>());
        authorizationInfo.setStringPermissions(new HashSet<String>());

        return authorizationInfo;
    }

    //认证登陆
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String mobile = authenticationToken.getPrincipal().toString();
        UserBO user = userBiz.getByMobile(mobile);
        if(user==null){
            log.info("user unregistered, mobile:{}", mobile);
            throw new UnknownAccountException("登录失败，该手机号未注册");
        }
        /*
        //校验门店
        ShopBO shopBO = user.getShopBO();
        if(shopBO==null){
            log.info("user shop is null, mobile:{}", mobile);
            throw new UnknownAccountException("登录失败，该手机号未注册");
        }
        //验证门店状态
        if(shopBO.getShopStatus()==null || shopBO.getShopStatus() != ConstantBean.SHOP_STATUS_OK){
            log.info("shop status exception, mobile:{}, status:{}", mobile, shopBO.getShopStatus());
            throw new UnknownAccountException("登录失败，门店状态异常");
        }
        //验证认证状态
        if(shopBO.getVerifyStatus()==null || shopBO.getVerifyStatus() == ConstantBean.SHOP_VERIFY_STATUS_FAILED){
            log.info("shop verify failed, mobile:{}, status:{}", mobile, shopBO.getVerifyStatus());
            throw new UnknownAccountException("登录失败，门店认证失败");
        }
        */

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(mobile, user.getPassword(), getName());
        shiroBiz.setCurrentUser(user);

        //设置当前用户历史页面访问记录
        commonBiz.setUserPvHisRecord(mobile);

        return authenticationInfo;
    }

}
