package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.CHAT_REQUEST_MESSAGE;

public class ChatRequestMessage implements Message, Serializable {

    private final String from;
    private final String to;
    private final String content;

    public ChatRequestMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ChatRequestMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public Integer getMessageType() {
        return CHAT_REQUEST_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
