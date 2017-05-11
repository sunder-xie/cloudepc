package com.tqmall.data.epc.web.filter;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.sys.UserBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.utils.CookieUtil;
import com.tqmall.data.epc.common.utils.EPCUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动登录过滤器
 * 需要在shiro-config中配置才能使用
 * Created by huangzhangting on 16/7/15.
 */
@Slf4j
public class AutoLoginFilter extends PathMatchingFilter {

    @Autowired
    private UserBiz userBiz;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        UserBO user = null;
        Subject subject = SecurityUtils.getSubject();
        if(subject != null) {
            Session session = subject.getSession();
            user = (UserBO)session.getAttribute(ConstantBean.USER_KEY);
        }
        if(user == null){
            String userInfo = CookieUtil.getUserLoginInfo((HttpServletRequest)request);
            if(userInfo != null && subject != null){
                UsernamePasswordToken token = new UsernamePasswordToken(userInfo, "");
                try {
                    subject.login(token);
                    CookieUtil.setUserLoginInfo((HttpServletResponse)response, userInfo);
                }catch (Exception e){
                    log.info("user auto login failed, userInfo:{}", userInfo);
                }
            }
        }

        //设置认证状态
        userBiz.resetVerifyStatus();

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        if(subject!=null && !EPCUtil.isAjaxRequest(httpServletRequest)){
            String uri = httpServletRequest.getRequestURI();
            String queryString = httpServletRequest.getQueryString();
            if(queryString != null) {
                uri += "?"+queryString;
            }
            Session session = subject.getSession();
            session.setAttribute(ConstantBean.LAST_URI, uri);
        }

        // Always return true since we allow access to anyone
        return true;
    }
}
