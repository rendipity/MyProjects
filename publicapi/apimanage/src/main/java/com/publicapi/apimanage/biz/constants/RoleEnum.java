package com.publicapi.apimanage.biz.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    // 角色
    ADMIN("admin",0),

    DEVELOPER("developer",1),

    USER("user",2);

    private String name;

    private Integer value;
}
