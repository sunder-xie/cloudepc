package com.tqmall.data.epc.web.webSocket.common;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * webSocket 相关常量
 * Created by lyj on 16/7/13.
 */
public class Constant {

    /**
     * final常量
     */
    //webSocket主机、端口、全URL 配置信息
    public static final String APPLICATION_PROPERTIES = "/application.properties";
    public static final String BASE_SOCKET_URL = "http://%s:%s";
    public static final String ATTR_NAME_SOCKET_HOST = "socket.host";
    public static final String ATTR_NAME_SOCKET_PORT = "socket.port";

    //默认webSocket主机、端口
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 19333;

    //webSocket eventNames
    public static final String EN_ORDER_STATE = "orderState";
    public static final String EN_SERVER_REPLY = "serverReply";
    public static final String EN_SERVER_PUSH = "serverPush";
    public static final String EN_SHOP_ID = "shopId";
    public static final Map<String, String> EVENT_NAMES;

    //可以连接服务器
    public static final boolean CAN_CONNECT_SERVER = true;

    /**
     * 普通可变常量
     */
    public static Map<UUID, SocketIOClient> CLIENTS = new HashMap<>();
    public static Map<Integer, UUID> SHOP_X_SESSION = new HashMap<>();

    /**
     * 常量初始化
     */
    static {
        EVENT_NAMES = new HashMap<>();
        EVENT_NAMES.put("EN_ORDER_STATE", EN_ORDER_STATE);
        EVENT_NAMES.put("EN_SERVER_REPLY", EN_SERVER_REPLY);
        EVENT_NAMES.put("EN_SERVER_PUSH", EN_SERVER_PUSH);
        EVENT_NAMES.put("EN_SHOP_ID", EN_SHOP_ID);
    }
}
