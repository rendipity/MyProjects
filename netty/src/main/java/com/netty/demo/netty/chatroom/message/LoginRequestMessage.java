package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.LOGIN_REQUEST_MESSAGE;

public class LoginRequestMessage implements Message, Serializable {

    private static final long serialVersionUID = -1L;

    private String username;
    private String password;

    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Integer getMessageType() {
        return LOGIN_REQUEST_MESSAGE.getType();
    }

    // todo 后续完善序列号
    @Override
    public Integer getSequenceId() {
        return 0;
    }

    @Override
    public String toString() {
        return "LoginRequestMessage{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
