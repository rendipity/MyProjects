package com.publicapi.apimanage.biz.bo;

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
     * 
     */
    private String appkey;

    /**
     * 
     */
    private String appsecret;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String creator;
}