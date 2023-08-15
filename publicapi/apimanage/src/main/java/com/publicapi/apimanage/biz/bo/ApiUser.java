package com.publicapi.apimanage.biz.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName api_user
 */
@Data
public class ApiUser {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;


    /**
     * 
     */
    private String appKey;

    /**
     * 
     */
    private String appSecret;

    /**
     * 角色
     */
    private String role;

    /**
     * 头像
     */
    private String headPhoto;

    /**
     * 
     */
    private String status;


    /**
     * 上次登陆时间
     */
    private Date lastLoginTime;

    /**
     * 
     */
    private Date createTime;
}