package com.publicapi.apimanage.web.vo.user;

import lombok.Data;

@Data
public class UpdatePasswordVO {

    private String username;

    private String oldPassword;

    private String newPassword;
}
