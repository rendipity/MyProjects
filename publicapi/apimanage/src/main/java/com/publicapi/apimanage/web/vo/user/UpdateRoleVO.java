package com.publicapi.apimanage.web.vo.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateRoleVO {

    @NotEmpty(message = "[用户名不能为空]")
    private String  username;

    @NotEmpty(message = "[角色不能为空]")
    private String role;
}
