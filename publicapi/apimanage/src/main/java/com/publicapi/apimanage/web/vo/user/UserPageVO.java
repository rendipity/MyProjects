package com.publicapi.apimanage.web.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserPageVO {
    /**
     *
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickName;

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

    /**
     * 上次登陆时间
     */
    private Date lastLoginTime;
    /**
     * 创建时间
     */
    private Date createTime;
}
