package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.Ping_Message;

public class PingMessage implements Message, Serializable {

    private static final long serialVersionUID = 1L;


    @Override
    public Integer getMessageType() {
        return Ping_Message.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
