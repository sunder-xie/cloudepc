package com.tqmall.data.epc.web.webSocket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.web.webSocket.common.Constant;
import com.tqmall.data.epc.web.webSocket.dto.MessageDTO;
import com.tqmall.data.epc.web.webSocket.listener.ChatListener;
import com.tqmall.data.epc.web.webSocket.listener.ReceiveShopIdListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

/**
 * webSocket 服务端, 应用启动时即启动服务
 * Created by lyj on 16/7/13.
 */
//@Component
@Slf4j
public class Server {
    private SocketIOServer server;

    //地址和端口号, 初始使用默认主机和端口, 以备读取配置文件失败时使用
    public static String host = Constant.DEFAULT_HOST;
    public static int port = Constant.DEFAULT_PORT;

    static {
//        readConfig();
    }

    /**
     * 读取配置, 读取失败则使用默认配置项
     */
    private static void readConfig() {
        Properties properties = new Properties();
        InputStream inputStream = WebSocketController.class.getResourceAsStream(Constant.APPLICATION_PROPERTIES);
        try {
            properties.load(inputStream);
            host = properties.getProperty(Constant.ATTR_NAME_SOCKET_HOST).trim();
            port = Integer.parseInt(properties.getProperty(Constant.ATTR_NAME_SOCKET_PORT).trim());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("读取socket配置出错, 将使用默认配置!");

            host = Constant.DEFAULT_HOST;
            port = Constant.DEFAULT_PORT;
        }
    }

//    @PostConstruct
    private void startServer() throws InterruptedException {
        //webSocket配置
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        server = new SocketIOServer(config);

        /* 添加监听器 */
        //添加客户端连接监听器
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                UUID sessionId = socketIOClient.getSessionId();
                //新的客户端连接, 则保存该客户端
                Constant.CLIENTS.put(sessionId, socketIOClient);
                log.info("new client connected: sessionId=" + sessionId);

                System.out.println("addConnectListener: " + Constant.CLIENTS.size() + "----" + Constant.SHOP_X_SESSION.size());
            }
        });

        //添加客户端断开监听器
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                UUID sessionId = socketIOClient.getSessionId();

                //客户端断开连接, 则删除之前保存的该客户端
                Constant.CLIENTS.remove(sessionId);

                Set<Integer> keySet = Constant.SHOP_X_SESSION.keySet();
                for(Integer shopId: keySet){
                    if(sessionId.equals(Constant.SHOP_X_SESSION.get(shopId))){
                        Constant.SHOP_X_SESSION.remove(sessionId);
                    }
                }

                log.info("a client disconnect:sessionId=" + socketIOClient.getSessionId());
                System.out.println(Constant.CLIENTS.size() + "----" + Constant.SHOP_X_SESSION.size());
            }
        });

        //添加客户端应答的监听器
        ChatListener chatListener = new ChatListener();
        chatListener.setServer(server);
        server.addEventListener(Constant.EN_SERVER_REPLY, MessageDTO.class, chatListener);

        //添加登录成功后处理的监听器
        ReceiveShopIdListener receiveShopIdListener = new ReceiveShopIdListener();
        receiveShopIdListener.setServer(server);
        server.addEventListener(Constant.EN_SHOP_ID, UserBO.class, receiveShopIdListener);

        //不断尝试启动服务, 直到启动成功跳出
        while (true) {
            try {
                server.start();
                break;
            } catch (Exception e) {
                //启动失败异常打印
                log.error("server start failure!");
                e.printStackTrace();

                //休眠1秒, 继续尝试启动服务
                Thread.sleep(1000);
            }
        }
        log.info("server start succeed!");
    }

//    @PreDestroy
    public void stopServer() {
        //关闭服务
        server.stop();

        //打印关闭服务成功日志
        log.info("server stop succeed!");
    }
}
