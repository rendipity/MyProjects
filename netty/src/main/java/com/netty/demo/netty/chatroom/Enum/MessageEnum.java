package com.netty.demo.netty.chatroom.Enum;

public enum MessageEnum {

    LOGIN_REQUEST_MESSAGE(0),
    LOGIN_RESPONSE_MESSAGE(1),
    CHAT_REQUEST_MESSAGE(2),
    CHAT_RESPONSE_MESSAGE(3),
    GROUP_CREATE_REQUEST_MESSAGE(4),
    GROUP_CREATE_RESPONSE_MESSAGE(5),
    GROUP_CHAT_REQUEST_MESSAGE(6),
    GROUP_CHAT_RESPONSE_MESSAGE(7),
    ;

    private Integer type;

    MessageEnum(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return type;
    }
}
