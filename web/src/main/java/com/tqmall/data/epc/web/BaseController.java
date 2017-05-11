package com.tqmall.data.epc.web;

import com.tqmall.data.epc.biz.car.CenterCarBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zxg on 16/2/2.
 * 11:38
 */
 public class BaseController {
    @Value(value = "${web.version}")
    protected String webVersion;
    @Value("${monk.host}")
    protected String monkDomain;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Autowired
    protected ShiroBiz shiroBiz;
    @Autowired
    private CenterCarBiz centerCarBiz;


    @ModelAttribute
    public void setReqAndRes(HttpServletRequest req, HttpServletResponse res){
        request = req;
        response = res;

        putIntoSession("monkDomain", monkDomain);

        putIntoSession("ossImage", ImgUtil.IMG_DOMAIN);

        putIntoSession("allCarBrands", centerCarBiz.getCarBrands());

    }

    protected void putIntoSession(String key, Object value){
        shiroBiz.putIntoSession(key, value);
    }

    //设置位置导航类型
    protected void setBreadcrumb(String value){
        putIntoSession("BREADCRUMB", value);
    }

}
