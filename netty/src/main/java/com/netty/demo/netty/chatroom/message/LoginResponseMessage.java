package com.netty.demo.netty.chatroom.message;

import java.io.Serializable;

import static com.netty.demo.netty.chatroom.Enum.MessageEnum.LOGIN_RESPONSE_MESSAGE;

public class LoginResponseMessage implements Message, Serializable {

    private final Boolean success;

    private final String  message;

    public LoginResponseMessage(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static LoginResponseMessage success(){
        return new LoginResponseMessage(true,"登陆成功");
    }

    public static LoginResponseMessage fail(){
        return new LoginResponseMessage(false,"用户名或者密码不正确");
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
