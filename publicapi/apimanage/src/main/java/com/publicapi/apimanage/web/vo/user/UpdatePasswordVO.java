package com.publicapi.apimanage.web.vo.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdatePasswordVO {

    @NotEmpty(message = "[原密码]不能为空")
    private String oldPassword;

    @NotEmpty(message = "[新密码]不能为空")
    private String newPassword;
}
