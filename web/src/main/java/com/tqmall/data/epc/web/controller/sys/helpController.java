package com.tqmall.data.epc.web.controller.sys;

import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhouheng on 16/8/12.
 */
@Controller
@RequestMapping("help")
@Slf4j
public class helpController extends BaseController{

    /**
     * 简介 Introduction
     */
    @RequestMapping("introduction")
    public ModelAndView getIntroduction(Integer type){
        ModelAndView modelAndView = new ModelAndView(webVersion+"/cloudepc/center/helpCenter");
        modelAndView.addObject("TOP_MSG", "帮助中心");
        modelAndView.addObject("type", type);
        return modelAndView;
    }
}
