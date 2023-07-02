package com.netty.demo.netty.chatroom.message;

import com.netty.demo.netty.chatroom.Enum.ErrorResultEnum;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.GROUP_CHAT_RESPONSE_MESSAGE;

public class GroupChatResponseMessage implements Message, Serializable {
    private static final long serialVersionUID = -1L;

    private Boolean success;
    private String errorMessage;
    private String from;
    private String content;

    public GroupChatResponseMessage(Boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }
    public static GroupChatResponseMessage fail(ErrorResultEnum errorResultEnum){
        return new GroupChatResponseMessage(false, errorResultEnum.getMessage());
    };

    public GroupChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
        this.success = true;
    }

    public Boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFrom() {
        return from;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "GroupChatResponseMessage{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                ", from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public Integer getMessageType() {
        return GROUP_CHAT_RESPONSE_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
