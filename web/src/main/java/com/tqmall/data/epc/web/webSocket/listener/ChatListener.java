package com.tqmall.data.epc.web.webSocket.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.tqmall.data.epc.web.webSocket.common.Constant;
import com.tqmall.data.epc.web.webSocket.dto.MessageDTO;

/**
 * 应答客户端信息
 * Created by lyj on 16/7/13.
 */
public class ChatListener implements DataListener<MessageDTO> {
    SocketIOServer server;

    public void setServer(SocketIOServer server) {
        this.server = server;
    }

    public void onData(SocketIOClient client, MessageDTO messageDTO, AckRequest ackSender) throws Exception {
        messageDTO.setContent(messageDTO.getContent() + "lyj");
        this.server.getBroadcastOperations().sendEvent(Constant.EN_SERVER_REPLY, messageDTO);
    }
}
