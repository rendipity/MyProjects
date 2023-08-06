package com.publicapi.apimanage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageEnum {

    Test("阿里云短信测试","SMS_154950909","短信测试模版"),
    REGISTER_AUTH_CODE("LJ开放平台","SMS_462011267","注册账号验证码"),
    SENSITIVE_AUTH_CODE("LJ开放平台","SMS_461986229","敏感信息验证码"),
    ;
    private String signName;
    private String templateCode;
    private String desc;
}
