package com.tqmall.data.epc.web.webSocket;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.web.webSocket.common.Constant;
import com.tqmall.data.epc.web.webSocket.event.OrderState;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * webSocket 相关请求处理
 * Created by lyj on 16/7/13.
 */
@Controller
@RequestMapping("webSocket")
@Slf4j
public class WebSocketController {
    @Autowired
    private ShiroBiz shiroBiz;

    @Autowired
    private OrderState orderState;

    @RequestMapping(value = "getWebSocketProperties")
    @ResponseBody
    public Result getWebSocketProperties() {
        String socketUrl = String.format(Constant.BASE_SOCKET_URL, Server.host, Server.port);

        Map<String, Object> map = new HashMap<>();
        map.put("socketUrl", socketUrl);
        map.put("eventNames", Constant.EVENT_NAMES);

        return ResultUtil.successResult(map);
    }

    @RequestMapping(value = "getCurrentUser")
    @ResponseBody
    public Result getCurrentUser() {
        Map<String, Object> map = new HashMap<>();

        UserBO sysUser = shiroBiz.getCurrentUser();
        if (sysUser != null) {
            String socketUrl = String.format(Constant.BASE_SOCKET_URL, Server.host, Server.port);
            map.put("socketUrl", socketUrl);
            map.put("eventNames", Constant.EVENT_NAMES);
        }

        map.put("currentUser", sysUser);
        return ResultUtil.successResult(map);
    }

    @RequestMapping(value = "pushOrderState")
    @ResponseBody
    public Result pushOrderState(Integer flag) throws InterruptedException {
        //推送订单状态到client
        orderState.sendEvent(Constant.CLIENTS, flag);

        return ResultUtil.successResult("pushOrderState succeed");
    }

}