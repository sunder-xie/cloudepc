package com.tqmall.data.epc.web;

import com.tqmall.data.epc.biz.forbidSpider.SpiderBiz;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zxg on 2016/07/03.
 * 10:25
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Controller
@RequestMapping("epc/captcha")
@Slf4j
public class captchaController {

    @Value(value = "${web.version}")
    private String webVersion;

    @Autowired
    private SpiderBiz spiderBiz;


    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView(webVersion+"/cloudepc/captcha");
        String ip = EPCUtil.getIpAddress(request);
        String refuseFtl = "/epc/captcha/index";
        //是否在黑名单中
        Boolean isInBlack = spiderBiz.isInBlackSet(ip);
        //默认在黑名单中
        Integer overNum = -1;
        Integer countDown = 0;
        if(!isInBlack){
            overNum = spiderBiz.getOverTimeNum(ip);
            countDown = spiderBiz.getCountDownTime(ip);

            if(overNum == null){
                //解封
                overNum = -2;
            }
        }

        modelAndView.addObject("overNum",overNum);
        modelAndView.addObject("time",countDown);
        modelAndView.addObject("refuseFtl",refuseFtl);
        modelAndView.addObject("ip",ip);
        return modelAndView;
    }

   //清除计数
    @RequestMapping(value = "giveWorldClean", method = RequestMethod.POST)
    @ResponseBody
    public Result clear(HttpServletRequest request){
        String ip = EPCUtil.getIpAddress(request);
        //是否在黑名单中
        Boolean isInBlack = spiderBiz.isInBlackSet(ip);
        if(!isInBlack){
            Integer overNum = spiderBiz.getOverTimeNum(ip);

            if(overNum == null){
                //清空其ip
                spiderBiz.clearIp(ip);
            }
        }

        return ResultUtil.successResult("useful");
    }
}
