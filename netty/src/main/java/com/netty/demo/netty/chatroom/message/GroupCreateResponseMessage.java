package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.GROUP_CREATE_RESPONSE_MESSAGE;

public class GroupCreateResponseMessage implements Message, Serializable {

    private static final long serialVersionUID = -1L;

    private final Boolean success;
    private String errorMessage;
    private String from;
    private String groupName;
    private String content;

    public GroupCreateResponseMessage(String from, String groupName, String content) {
        this.from = from;
        this.groupName = groupName;
        this.content = content;
        this.success = true;
    }

    public GroupCreateResponseMessage(Boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
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

    public String getGroupName() {
        return groupName;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "GroupCreateResponseMessage{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                ", from='" + from + '\'' +
                ", groupName='" + groupName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public Integer getMessageType() {
        return GROUP_CREATE_RESPONSE_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
