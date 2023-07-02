package com.netty.demo.netty.chatroom.Enum;

public enum ErrorResultEnum {

    GROUP_EXIST("0001","该群已经存在了"),
    GROUP_NOT_EXIST("0002","该群不存在");

    private String errorCode;
    private String message;

    ErrorResultEnum(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
