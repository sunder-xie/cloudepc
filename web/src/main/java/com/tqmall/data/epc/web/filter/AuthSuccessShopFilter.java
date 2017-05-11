package com.tqmall.data.epc.web.filter;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.utils.EPCUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 登录账号是认证通过的门店
 * Created by huangzhangting on 16/7/25.
 */
@Slf4j
public class AuthSuccessShopFilter extends UserFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断是否登录请求
        if (isLoginRequest(request, response)) {
            return true;
        }
        Subject subject = getSubject(request, response);
        // If principal is not null, then the user is known and should be allowed access.
        if(subject.getPrincipal() == null){
            return false;
        }

        Session session = subject.getSession();
        UserBO user = (UserBO)session.getAttribute(ConstantBean.USER_KEY);
        if(user == null){
            return false;
        }
        Result<String> result = UserUtil.checkShopVerifyStatus(user.getShopBO());
        if(result.isSuccess()){
            return true;
        }
        request.setAttribute("notAllowedMsg", result.getMessage());
        return false;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String url;
        String jsonStr;
        Object notAllowedMsg = request.getAttribute("notAllowedMsg");
        if(notAllowedMsg == null){
            url = getLoginUrl();
            jsonStr = UserUtil.getTimeOutJsonStr();
        }else{
            //返回到认证页面
//            url = "/user/shopAuthPage";
            url = "/home?msg=" + ConstantBean.UN_AUTH_SHOP_KEY;
            jsonStr = UserUtil.getUnVerifySuccessJsonStr(notAllowedMsg.toString());
        }

        //判断ajax请求
        if(EPCUtil.isAjaxRequest((HttpServletRequest)request)){
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            OutputStream out = response.getOutputStream();
            out.write(jsonStr.getBytes("UTF-8"));
            out.flush();
            out.close();

        }else {
            WebUtils.issueRedirect(request, response, url);
        }

    }
}
