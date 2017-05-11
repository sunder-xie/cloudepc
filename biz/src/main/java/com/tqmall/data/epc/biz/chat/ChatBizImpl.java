package com.tqmall.data.epc.biz.chat;

import com.tqmall.data.epc.exterior.dubbo.monk.MonkExt;
import com.tqmall.data.monk.client.bean.MonkClientConstants;
import com.tqmall.data.monk.client.bean.dto.ChatUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxg on 16/9/9.
 * 10:43
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
public class ChatBizImpl implements ChatBiz {

    @Autowired
    private MonkExt monkExt;

    @Value("${monk.epc.server.id}")
    protected Integer serverId;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private static String NEW_ORDER_MESSAGE = "您好，客户已提交新订单:%s，总金额%s元。可能涉及订单改价，发货事宜，请尽快在云配系统进行确认和处理~";
    private static String CHANGE_PRICE_MESSAGE = "您好，订单:%s已改价，请刷新页面，确认金额后进行支付~";
    private static String SHIPPING_MESSAGE = "您好，订单:%s,供应商已经发货，可以到订单详情页面确认发货信息。收到货物后请及时到汽配管家系统中做签收~";
    private static String RECEIVE_MESSAGE = "您好，订单:%s的商品,客户已签收，可以到订单详情页面确认~";

    //新订单通知，门店(shop_id)通知供应商(org_id)
    @Override
    public void newOrderNotify(Integer shopId,String shopName,Integer orgId,String orgName,String orgTel,
                               String orderSn,String priceAmount) {
        if(null == shopId || null == orgId || StringUtils.isEmpty(orderSn) || StringUtils.isEmpty(priceAmount)){
            return ;
        }

        String orderMessage = String.format(NEW_ORDER_MESSAGE,orderSn,priceAmount);
        shopToSeller(shopId, orgId, orderMessage);
        //发送一份给客服
        List<String> messageList = new ArrayList<>();
        messageList.add("平台通知：新的筛选购买订单被创建了");
        messageList.add("创建的门店: "+shopName);
        messageList.add("订单所在的供应商:"+orgName);
        messageList.add("供应商联系号码:"+orgTel);

        MessageToServer(messageList);
    }

    //改价 im 通知，供应商(org_id)通知门店(shop_id)
    @Override
    public void changePriceNotify(Integer shopId, Integer orgId, String orderSn) {
        if(null == shopId || null == orgId || StringUtils.isEmpty(orderSn) ){
            return ;
        }

        String changeMessage = String.format(CHANGE_PRICE_MESSAGE, orderSn);
        sellerToShop(shopId, orgId, changeMessage);
    }

    //发货im通知，供应商(org_id)通知门店(shop_id)
    @Override
    public void haveShippingNotify(Integer shopId, Integer orgId, String orderSn) {
        if(null == shopId || null == orgId || StringUtils.isEmpty(orderSn) ){
            return ;
        }

        String shippingMessage = String.format(SHIPPING_MESSAGE, orderSn);
        sellerToShop(shopId, orgId, shippingMessage);
    }

    //客户签收im通知，门店(shop_id)通知供应商(org_id)
    @Override
    public void haveReceiveNotify(Integer shopId, Integer orgId, String orderSn) {
        if(null == shopId || null == orgId || StringUtils.isEmpty(orderSn) ){
            return ;
        }

        String receiveMessage = String.format(RECEIVE_MESSAGE, orderSn);
        shopToSeller(shopId, orgId, receiveMessage);
    }

    @Override
    public void newWishOrderNotify(String companyName, String carName, String cityName) {
        List<String> messageList = new ArrayList<>();
        messageList.add("平台通知：新的需求单被创建了");
        messageList.add("创建的门店: "+companyName);
        messageList.add("需求单所在的车型:"+carName);
        messageList.add("需求单所在的城市:"+cityName);


        MessageToServer(messageList);
    }


    /*====private==*/
    //多线程 发消息
    private class ImMessage implements Runnable {
        private ChatUserDto chatUserDto;
        private List<ChatUserDto> toChatDtoList;
        private List<String> messageList;
        
        ImMessage(ChatUserDto chatUserDto, List<ChatUserDto> toChatDtoList, List<String> messageList){
            this.chatUserDto = chatUserDto;
            this.toChatDtoList = toChatDtoList;
            this.messageList = messageList;
        }
        
        @Override
        public void run() {
            try {
                monkExt.sendMessage(chatUserDto,toChatDtoList,messageList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    //供应商发消息给门店
    private void sellerToShop(Integer shopId, Integer orgId, String message){
        ChatUserDto fromUser = new ChatUserDto();
        fromUser.setSysId(orgId);
        fromUser.setSysName(MonkClientConstants.SYSNAME_YUN_PEI);

        List<ChatUserDto> toUsers = new ArrayList<>();
        ChatUserDto toDO = new ChatUserDto();
        toDO.setSysId(shopId);
        toDO.setSysName(MonkClientConstants.SYSNAME_UC);
        toUsers.add(toDO);


        List<String> messageList = new ArrayList<>();
        messageList.add(message);

        ImMessage imMessage = new ImMessage(fromUser, toUsers, messageList);
        taskExecutor.execute(imMessage);
    }

    //门店给供应商
    private void shopToSeller(Integer shopId, Integer orgId, String message){
        ChatUserDto fromUser = new ChatUserDto();
        fromUser.setSysId(shopId);
        fromUser.setSysName(MonkClientConstants.SYSNAME_UC);

        List<ChatUserDto> toUsers = new ArrayList<>();
        ChatUserDto toDO = new ChatUserDto();
        toDO.setSysId(orgId);
        toDO.setSysName(MonkClientConstants.SYSNAME_YUN_PEI);
        toUsers.add(toDO);


        List<String> messageList = new ArrayList<>();
        messageList.add(message);

        ImMessage imMessage = new ImMessage(fromUser, toUsers, messageList);
        taskExecutor.execute(imMessage);
    }

    //给客服
    private void MessageToServer(List<String> messageList){
        ChatUserDto fromUser = new ChatUserDto();
        fromUser.setSysId(0);
        fromUser.setSysName(MonkClientConstants.SYSNAME_UC);

        List<ChatUserDto> toUsers = new ArrayList<>();
        ChatUserDto toDO = new ChatUserDto();
        toDO.setSysId(serverId);
        toDO.setSysName(MonkClientConstants.SYSNAME_IM_SERVER);
        toUsers.add(toDO);

        ImMessage imMessage = new ImMessage(fromUser, toUsers, messageList);
        taskExecutor.execute(imMessage);
    }


    
}
