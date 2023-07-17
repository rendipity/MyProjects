package com.publicapi.apimanage.web.vo.user;

import lombok.Data;

@Data
public class RegisterUserVO {

    private String phone;

    private String username;

    private String password;

    private String authCode;
}
