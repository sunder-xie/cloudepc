package com.tqmall.data.epc.exterior.dubbo.monk;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.monk.client.bean.dto.ChatUserDto;
import com.tqmall.data.monk.client.service.MonkSendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zxg on 16/9/9.
 * 10:28
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Slf4j
@Service
public class MonkExtImpl implements MonkExt {
    @Autowired
    private MonkSendMessageService monkSendMessageService;

    @Override
    public Boolean sendMessage(ChatUserDto fromChatUserDto, List<ChatUserDto> toChatDtoList, List<String> messageList) {
        if(null == fromChatUserDto || CollectionUtils.isEmpty(toChatDtoList) || CollectionUtils.isEmpty(messageList)){
            return false;
        }
        log.info("from:{},to:{},message:{}", JsonUtil.objectToJson(fromChatUserDto),toChatDtoList.toString(),messageList.toString());

        Result result = monkSendMessageService.sendMessageToOther(fromChatUserDto,toChatDtoList,messageList);

        log.info("send im message result:{}",JsonUtil.objectToJson(result));
        if(result.isSuccess()){
            return true;
        }
        return false;
    }
}
