package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;
import java.util.Set;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.GROUP_CREATE_REQUEST_MESSAGE;


public class GroupCreateRequestMessage implements Message, Serializable {

    private static final long serialVersionUID = -1L;

    private final String from;
    private final String groupName;
    private final Set<String> members;

    public GroupCreateRequestMessage(String from, String groupName, Set<String> members) {
        this.from = from;
        this.groupName = groupName;
        this.members = members;
    }

    public String getFrom() {
        return from;
    }

    public String getGroupName() {
        return groupName;
    }

    public Set<String> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "GroupCreateRequestMessage{" +
                "from='" + from + '\'' +
                ", groupName='" + groupName + '\'' +
                ", members=" + members +
                '}';
    }

    @Override
    public Integer getMessageType() {
        return GROUP_CREATE_REQUEST_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
