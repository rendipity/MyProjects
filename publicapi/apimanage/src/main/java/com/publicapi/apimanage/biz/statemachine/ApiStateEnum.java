package com.publicapi.apimanage.biz.statemachine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiStateEnum {
    ENABLEING("enable","启用中"),
    DISABLEING("disable","禁用中");
    private String code;
    private String desc;
}
