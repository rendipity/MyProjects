package com.publicapi.apimanage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResultEnum {

    API_NOT_EXIST("00001","api不存在"),
    ABNORMAL_STATE("00002","状态变更异常异常"),
    PARAMETER_EXCEPTION("00003","参数异常"),
    ;
    private String errorCode;
    private String errorMessage;
}