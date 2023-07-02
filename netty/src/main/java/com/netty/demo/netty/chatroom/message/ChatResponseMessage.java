package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.CHAT_MESSAGE;

public class ChatResponseMessage implements Message, Serializable {

    private  String from;
    private  String to;
    private  String content;

    private final boolean success;
    private String errorMessage;

    public ChatResponseMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.success = true;
    }

    public ChatResponseMessage(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static ChatResponseMessage fail(String errorMessage){
        return new  ChatResponseMessage(false,errorMessage);
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

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ChatResponseMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", content='" + content + '\'' +
                ", success=" + success +
                ", errorMessage=" + errorMessage +
                '}';
    }

    @Override
    public Integer getMessageType() {
        return CHAT_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
