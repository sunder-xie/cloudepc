//package com.tqmall.data.epc.web.controller.center;
//
//import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
//import com.tqmall.data.epc.common.utils.ResultUtil;
//import com.tqmall.data.epc.web.BaseController;
//import com.tqmall.data.epc.web.webSocket.dto.MessageDTO;
//import com.tqmall.core.common.entity.Result;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.*;
//
///**
// * 个人中心 控制器
// * Created by lyj on 16/7/11.
// */
//@Controller
//@RequestMapping(value = {"center"})
//public class CenterController extends BaseController {
//
//    static Map<String, String> user = new HashMap<>();
//
//    static {
//        user = new HashMap<>();
//        user.put("account", "jyl");
//        user.put("phone", "18768111223");
//    }
//
//    @RequestMapping(value = {"center"})
//    public ModelAndView center() {
//        return new ModelAndView(webVersion + "/cloudepc/center/center");
//    }
//
//    @RequestMapping(value = {"myOrder"})
//    @ResponseBody
//    public Result myOrder() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("data", "asd");
//        return ResultUtil.successResult(map);
//    }
//
//    @RequestMapping(value = {"myAsset"})
//    @ResponseBody
//    public Result myAsset() {
//        Map<String, Object> map = new HashMap<>();
//        return ResultUtil.successResult(map);
//    }
//
//    @RequestMapping(value = {"myMessage"})
//    @ResponseBody
//    public Result myMessage() {
//        Date date = new Date();
//        //构造消息
//        MessageDTO messageDTO1 = new MessageDTO("sys", 44841, date, "订单号123, 下单成功!", 1, "系统消息");
//        MessageDTO messageDTO2 = new MessageDTO("sys", 44841, date, "订单号123, 付款成功!", 1, "系统消息");
//        MessageDTO messageDTO3 = new MessageDTO("sys", 44841, date, "订单号123, 出货成功!", 1, "系统消息");
//        MessageDTO messageDTO4 = new MessageDTO("sys", 44841, date, "订单号123, 已经签收!", 1, "系统消息");
//        MessageDTO messageDTO5 = new MessageDTO("sys", 44842, date, "订单号123, 已经签收!", 1, "系统消息");
//        MessageDTO messageDTO6 = new MessageDTO("sys", 44842, date, "订单号123, 已经签收!", 1, "系统消息");
//        List<MessageDTO> messages = new ArrayList<>();
//        messages.add(messageDTO1);
//        messages.add(messageDTO2);
//        messages.add(messageDTO3);
//        messages.add(messageDTO4);
//        messages.add(messageDTO5);
//        messages.add(messageDTO6);
//
//        UserBO sysUser = shiroBiz.getCurrentUser();
//        if (sysUser != null) {
//            System.out.println(sysUser.getShopId());
//            List<MessageDTO> temp = new ArrayList<>();
//            for (MessageDTO messageDTO : messages) {
//                if (shiroBiz.getCurrentUser().getShopId().equals(messageDTO.getTo())) {
//                    temp.add(messageDTO);
//                }
//            }
//            messages = temp;
//        }
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("messages", messages);
//
//        return ResultUtil.successResult(map);
//    }
//
//    @RequestMapping(value = {"accountSettings"})
//    @ResponseBody
//    public Result accountSettings() {
//        Map<String, Object> map = new HashMap<>();
//        return ResultUtil.successResult(map);
//    }
//
//    @RequestMapping(value = {"securityCenter"})
//    @ResponseBody
//    public Result securityCenter() {
//        Map<String, Object> map = new HashMap<>();
//        return ResultUtil.successResult(map);
//    }
//
//    @RequestMapping(value = {"test"})
//    @ResponseBody
//    public void test(String p) {
//        user.put("phone", p);
//    }
//
//    @RequestMapping(value = {"tt"})
//    public ModelAndView tt() {
//        return new ModelAndView(webVersion + "/cloudepc/center/socketTest");
//    }
//
//    @RequestMapping(value = {"getOrders"})
//    @ResponseBody
//    public Result getOrders() {
//        Map<String, Object> map = new HashMap<>();
////        map.put("orders", );
//        return ResultUtil.successResult(map);
//    }
//}
//
