package com.tqmall.data.epc.exterior.dubbo.monk;

import com.tqmall.data.monk.client.bean.dto.ChatUserDto;

import java.util.List;

/**
 * Created by zxg on 16/9/9.
 * 10:28
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface MonkExt {
    Boolean sendMessage(ChatUserDto chatUserDto, List<ChatUserDto> toChatDtoList, List<String> messageList);
}
