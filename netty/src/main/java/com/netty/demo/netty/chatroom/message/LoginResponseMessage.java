package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.LOGIN_RESPONSE_MESSAGE;

public class LoginResponseMessage implements Message, Serializable {

    private final Boolean success;


    public LoginResponseMessage(Boolean success) {
        this.success = success;
    }

    public static LoginResponseMessage success(){
        return new LoginResponseMessage(true);
    }

    public static LoginResponseMessage fail(){
        return new LoginResponseMessage(false);
    }


    public boolean isSuccess(){
        return success.equals(true);
    }
    @Override
    public Integer getMessageType() {
        return LOGIN_RESPONSE_MESSAGE.getType();
    }

    @Override
    public Integer getSequenceId() {
        return 0;
    }
}
