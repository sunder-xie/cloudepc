package com.tqmall.data.epc.web.controller.avid;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.biz.avid.AvidCallBiz;
import com.tqmall.data.epc.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Controller
@RequestMapping("avidCall")
public class AvidCallController extends BaseController{
    @Autowired
    private AvidCallBiz avidCallBiz;

//    TODO 业务方要求，暂时先不开放急呼入口
//    @RequestMapping("")
//    public ModelAndView index(){
//        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/avid/avidCall");
//
//        setBreadcrumb("AVID_CALL");
//
//        return modelAndView;
//    }

    @RequestMapping("create")
    @ResponseBody
    public Result<Integer> createAvidCall(Integer carSeriesId) {
        return avidCallBiz.createAvidCall(carSeriesId);
    }

}
