package com.tqmall.data.epc.web.interceptor;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.forbidSpider.SpiderBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.utils.CookieUtil;
import com.tqmall.data.epc.common.utils.EPCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zxg on 16/7/15.
 * 09:36
 * no bug,以后改代码的哥们，祝你好运~！！
 * 所有页面请求的页面展示
 */
@Slf4j
@Service
public class AllFtlInterceptor implements HandlerInterceptor {
    @Autowired
    private SpiderBiz spiderBiz;
    @Autowired
    private ShiroBiz shiroBiz;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String ip = EPCUtil.getIpAddress(request);
        String uri = request.getRequestURI();

        shiroBiz.putIntoSession("pageUriId", shiroBiz.getPageUriId(uri)); //用于页面判断是否展示引导

        UserBO user = shiroBiz.getCurrentUser();

        log.info("request record, ip:{}, mobile:{}, uri:{}, queryStr:{}", ip,
                user==null?"未登录用户":user.getMobile(), uri, request.getQueryString());

        //是否在黑名单中
        Boolean isInBlack = spiderBiz.isInBlackSet(ip);
        if(!isInBlack){

            //设置城市站
            if(!uri.endsWith(".html")) {
                if (shiroBiz.getSessionValue(ConstantBean.CURRENT_CITY_NAME_KEY) == null) {
                    shiroBiz.setUserCity(CookieUtil.getRegionInfo(request));
                }
            }

            //短期的禁止数
            Integer overNum = spiderBiz.getOverTimeNum(ip);

            if(overNum == null){
                // 没有超过阀值
                return true;
            }
        }

        String refuseFtl = "/epc/captcha/index";
        response.setHeader("refuseSpider", "true");
        response.setHeader("refuseFtl", refuseFtl);
        request.getRequestDispatcher(refuseFtl).forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
