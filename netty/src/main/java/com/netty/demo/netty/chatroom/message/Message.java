package com.netty.demo.netty.chatroom.message;

public interface Message {

    /**
     * 消息类型
     */
    Integer getMessageType();

    /**
     * * 序列号
     */
    Integer getSequenceId();

}

