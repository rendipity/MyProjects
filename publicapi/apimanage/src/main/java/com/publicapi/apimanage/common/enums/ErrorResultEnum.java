package com.publicapi.apimanage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResultEnum {

    // api 异常
    API_NOT_EXIST("00001","api不存在"),
    ABNORMAL_STATE("00002","状态变更异常异常"),
    PARAMETER_EXCEPTION("00003","参数异常"),
    URL_DUPLICATION("00004","url重复"),
    SYSTEM_EXCEPTION("00005","系统异常"),

    // user 异常
    SEND_AUTH_CODE_ERROR("00021","验证码发送失败，请重试"),
    SEND_AUTH_CODE_IS_LIMITED("00022","验证码已经发送，若未收到请 1 分钟后再试"),
    AUTH_CODE_ERROR("00023","验证码错误"),
    PHONE_HAS_EXISTED("00024","该手机号已经注册过用户"),
    USERNAME_HAS_EXISTED("00025","用户名已存在"),
    USERNAME_OR_PASSWORD_ERROR("00026","用户名或密码错误"),
    ACCESS_FORBIDDEN("00027","无权限访问"),
    USER_NOT_EXIST("00028","用户不存在"),
    PASSWORD_ERROR("00029","密码错误"),
    ROLE_NOT_EXIST("00030","角色不存在"),
    TOKEN_INVALID("00031","token已失效"),
    PHONE_INVALID("00032","手机号不合法"),
    IP_INVALID("00033","IP不合法"),
    ;
    private String errorCode;
    private String errorMessage;
}
