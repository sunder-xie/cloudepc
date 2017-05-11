package com.tqmall.data.epc.web.controller.home;

import com.tqmall.data.epc.biz.sys.UserBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页控制器
 * Created by lyj on 16/06/17.
 */
@Controller
@RequestMapping("home")
public class HomeController extends BaseController {
    @Autowired
    private UserBiz userBiz;

    @RequestMapping("")
    public ModelAndView index(String msg) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/home/index");
        if(ConstantBean.UN_AUTH_SHOP_KEY.equals(msg)) {
            view.addObject("HOME_MSG", userBiz.checkShopVerifyStatus().getMessage());
        }
        return view;
    }

    @RequestMapping("query")
    public ModelAndView query() {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/home/home");
        //view.addObject("GO_HOME", "go");
        return view;
    }
}
