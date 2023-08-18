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

    public static RoleEnum getByName(String roleName){
        for(RoleEnum role: RoleEnum.values()){
            if (role.getName().equals(roleName))
                return role;
        }
        return USER;
    }

}
