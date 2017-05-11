package com.tqmall.data.epc.web.controller.MonkChat;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.WishListImResultBO;
import com.tqmall.data.epc.bean.param.lop.ImWishSnParam;
import com.tqmall.data.epc.biz.lop.MonkChatBiz;
import com.tqmall.data.epc.biz.lop.WishListBiz;
import com.tqmall.data.epc.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * IM 处理器
 * Created by lyj on 16/8/11.
 * 开开心心写代码-lyj!
 */
@Controller
@RequestMapping("monkChat")
public class MonkChatController extends BaseController {
    @Autowired
    private MonkChatBiz monkChatBiz;
    @Autowired
    private WishListBiz wishListBiz;


    @RequestMapping(value = "index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/monkChat/monkChat");
        //modelAndView.addObject("monkDomain", monkDomain);

        return modelAndView;
    }

    @RequestMapping(value = "requestList", method = RequestMethod.GET)
    public ModelAndView requestList(String orderSn, String vin, String carType) {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/lop/chat/requestList");
        modelAndView.addObject("orderSn", orderSn);
        modelAndView.addObject("vin", vin);
        modelAndView.addObject("carType", carType);
        //modelAndView.addObject("monkDomain", monkDomain);

        return modelAndView;
    }

    @RequestMapping(value = "requirements", method = RequestMethod.GET)
    public ModelAndView requirements(Integer sellerId) {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/lop/chat/requirements");
        Result<List<String>> cacheWishSnResult = monkChatBiz.getCacheWishSn(sellerId);
        if (cacheWishSnResult.isSuccess()) {
            modelAndView.addObject("orderSn", cacheWishSnResult.getData());
        }
        modelAndView.addObject("sellerId", sellerId);
        //modelAndView.addObject("monkDomain", monkDomain);

        return modelAndView;
    }

    /**
     * 缓存今日聊到的需求单编号
     * @param param
     * @return
     */
    @RequestMapping(value = "cacheWishSn", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<String>> cacheWishSn(@RequestBody ImWishSnParam param){
        return monkChatBiz.cacheWishSn(param);
    }

    /**
     * 查询缓存的需求单号
     * @param chatObjectId 聊天对象id
     * @return
     */
    @RequestMapping("getCacheWishSn")
    @ResponseBody
    public Result<List<String>> getCacheWishSn(Integer chatObjectId){
        return monkChatBiz.getCacheWishSn(chatObjectId);
    }

    /**
     * 查询需求单详情
     * @param wishListSn
     * @param sellerId
     * @return
     */
    @RequestMapping("groupedWishInfo")
    @ResponseBody
    public Result<WishListImResultBO> groupedWishInfo(String wishListSn, Integer sellerId) {
        return wishListBiz.groupedWishInfo(wishListSn, sellerId);
    }

}
