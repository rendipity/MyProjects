package com.publicapi.apimanage.biz.statemachine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiEventEnum {
    ENABLE("enable","启用"),
    DISABLE ("disable","禁用");
    private String code;
    private String desc;
}
