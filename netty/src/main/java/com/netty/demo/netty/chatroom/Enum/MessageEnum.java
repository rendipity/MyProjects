package com.netty.demo.netty.chatroom.Enum;

public enum MessageEnum {

    LOGIN_REQUEST_MESSAGE(0),
    LOGIN_RESPONSE_MESSAGE(1),
    CHAT_MESSAGE(2)
    ;

    private Integer type;

    MessageEnum(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return type;
    }
}
