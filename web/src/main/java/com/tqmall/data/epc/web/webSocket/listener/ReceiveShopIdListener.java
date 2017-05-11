package com.tqmall.data.epc.web.webSocket.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.web.webSocket.common.Constant;

/**
 * 接收客户端发送的shopId的监听器
 * Created by lyj on 16/7/14.
 */
public class ReceiveShopIdListener implements DataListener<UserBO> {
    SocketIOServer server;

    public void setServer(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void onData(SocketIOClient socketIOClient, UserBO sysUser, AckRequest ackRequest) throws Exception {
        Constant.SHOP_X_SESSION.put(sysUser.getShopId() , socketIOClient.getSessionId());
        System.out.println("ReceiveShopIdListener: " + Constant.CLIENTS.size() + "----" + Constant.SHOP_X_SESSION.size());
    }
}
