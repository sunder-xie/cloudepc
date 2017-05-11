package com.tqmall.data.epc.web.webSocket.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.tqmall.data.epc.web.webSocket.common.Constant;
import com.tqmall.data.epc.web.webSocket.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 推送订单状态
 * Created by lyj on 16/7/13.
 */
@Component
@Slf4j
public class OrderState {

    public void sendEvent(Map<UUID, SocketIOClient> socketIOClients, Integer flag) throws InterruptedException {
        Random random = new Random();
        int[] shops = {44841, 44842, 44843, 44844, 44845};
        int i = 0;
        while (true) {
            int shopId = shops[random.nextInt(5)];
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent(String.valueOf(shopId));
            messageDTO.setDate(new Date());
            messageDTO.setForm("系统");
            messageDTO.setTo(shopId);
            messageDTO.setType(1);
            messageDTO.setTypeName("系统推送");

            UUID sessionId = Constant.SHOP_X_SESSION.get(shopId);
            SocketIOClient socketIOClient = Constant.CLIENTS.get(sessionId);
            if (socketIOClient != null) {
                socketIOClient.sendEvent(Constant.EN_ORDER_STATE, messageDTO);
            }

            i++;

            Thread.sleep(1000);
            if (i > 30) {
                break;
            }
        }
    }
}
