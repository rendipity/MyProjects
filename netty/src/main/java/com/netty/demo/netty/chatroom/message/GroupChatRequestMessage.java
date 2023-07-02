package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.GROUP_CHAT_REQUEST_MESSAGE;

public class GroupChatRequestMessage implements Message, Serializable {
    private static final long serialVersionUID = -1L;

    private String from;
    private String groupName;
    private String content;

    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.from = from;
        this.groupName = groupName;
        this.content = content;
    }

    @Override
    public String toString() {
        return "GroupChatRequestMessage{" +
                "from='" + from + '\'' +
                ", groupName='" + groupName + '\'' +
                ", content='" + content + '\'' +
                '}';
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
    public Integer getMessageType() {
        return GROUP_CHAT_REQUEST_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
