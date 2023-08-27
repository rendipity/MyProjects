package com.publicapi.enums;

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
    AUTH_CODE_IS_EMPTY("00034","验证码不能为空"),


    // gateway
    RPC_SERVICE_EXCEPTION("00101","系统异常"),
    APP_KEY_NOT_EXIST("00102","认证信息不存在"),
    TIMESTAMP_INVALID("00103","时间戳不合法"),
    SIGN_INVALID("00104","签名不合法"),
    REQUEST_REPEAT("00105","请勿重复请求"),
    INVOKE_TIME_NOT_ENOUGH("00106","调用次数已耗尽"),
    OVER_THE_RATE_LIMIT("00107","接口使用频繁，请稍后重试..."),
    ROUTE_NOT_EXIST("00108","接口不存在"),
    ;
    private String errorCode;
    private String errorMessage;
}
