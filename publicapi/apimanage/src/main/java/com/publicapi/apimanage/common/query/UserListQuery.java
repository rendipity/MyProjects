package com.publicapi.apimanage.common.query;

import lombok.Data;

@Data
public class UserListQuery {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色
     */
    private String role;

    /**
     * 状态
     */
    private String status;
}
