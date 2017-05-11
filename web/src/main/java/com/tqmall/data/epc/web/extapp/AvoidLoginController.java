package com.tqmall.data.epc.web.extapp;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.param.LegendLoginParam;
import com.tqmall.data.epc.common.bean.enums.UserType;
import com.tqmall.data.epc.common.utils.*;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 外部系统免登录
 * Created by huangzhangting on 16/8/10.
 */
@Slf4j
@Controller
@RequestMapping("avoidLogin")
public class AvoidLoginController extends BaseController {
    @Value("${legend.key.prefix}")
    private String legendKeyPrefix;
    @Value("${legend.key.suffix}")
    private String legendKeySuffix;


    @RequestMapping("legendLogin")
    public String legendLogin(String param, String sign){
        String home = "redirect:/home";
        String loginPage = "redirect:/user/loginPage";
        if(StringUtils.isEmpty(param) || StringUtils.isEmpty(sign)){
            return loginPage;
        }

        log.info("legend login, param:{}, sign:{}", param, sign);

        String md5Str = MD5Utils.MD5(legendKeyPrefix + param + legendKeySuffix);
        if (!sign.equalsIgnoreCase(md5Str)) {
            return loginPage;
        }
        String code = Base64Util.decode(param, "utf-8");
        LegendLoginParam loginParam = JsonUtil.jsonToObject(code, LegendLoginParam.class);
        if(loginParam==null || !EPCUtil.isMobile(loginParam.getMobile())){
            return loginPage;
        }

        String mobile = loginParam.getMobile();
        UserBO user = shiroBiz.getCurrentUser();
        //已经登录，未退出
        if(user != null && mobile.equals(user.getMobile())){
            log.info("legend login, user not logout, mobile:{}", mobile);
            return home;
        }
        Integer city = loginParam.getCityId();

        log.info("legend login, mobile:{}, city:{}", mobile, city);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mobile, "");
        try {
            //登录
            subject.login(token);
            //往cookie写登录信息
            CookieUtil.setUserLoginInfo(response, mobile);
            CookieUtil.setRegionInfo(response, city);

            //设置用户类型
            shiroBiz.getCurrentUser().setUserTypeCode(UserType.YUN_XIU.getCode());
            shiroBiz.setUserCity(city);
            return home;
        }catch (AuthenticationException ae){
            shiroBiz.removeCurrentUser();
            request.setAttribute("message", ae.getMessage());
            log.info("legend login failed, message:{}", ae.getMessage());
        }catch (Exception e){
            shiroBiz.removeCurrentUser();
            request.setAttribute("message", "登录失败，系统内部错误");
            log.error("legend login error", e);
        }
        return loginPage;
    }
}
