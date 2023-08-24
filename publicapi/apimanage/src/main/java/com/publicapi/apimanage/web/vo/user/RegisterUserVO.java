package com.publicapi.apimanage.web.vo.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterUserVO {

    @NotEmpty(message = "[手机号]不能为空")
    private String phone;

    @NotEmpty(message = "[用户名]不能为空")
    private String username;

    @NotEmpty(message = "[密码]不能为空")
    private String password;

    @NotEmpty(message = "[验证码]不能为空")
    private String authCode;
}
