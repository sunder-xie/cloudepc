package com.tqmall.data.epc.web.webSocket.dto;

import lombok.Data;

import java.util.Date;

/**
 * 消息实体
 * Created by lyj on 16/7/13.
 */
@Data
public class MessageDTO {
    private String form;
    private Integer to;
    private Date date;
    private String content;
    private int type;
    private String typeName;

    public MessageDTO() {
        super();
    }

    public MessageDTO(String form, Integer to, Date date, String content, int type, String typeName) {
        this.form = form;
        this.to = to;
        this.date = date;
        this.content = content;
        this.type = type;
        this.typeName = typeName;
    }

}
