package com.publicapi.apimanage.dao.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName api_user
 */
@TableName(value ="api_user")
@Data
public class ApiUserDO implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 
     */
    @TableField(value = "app_key")
    private String appKey;

    /**
     * 
     */
    @TableField(value = "app_secret")
    private String appSecret;

    /**
     * 角色
     */
    @TableField(value = "role")
    private String role;

    /**
     * 头像
     */
    @TableField(value = "head_photo")
    private String headPhoto;

    /**
     * 调用次数
     */
    @TableField(value = "invoke_times")
    private Integer invokeTimes;

    /**
     * 总的调用次数
     */
    @TableField(value = "total_times")
    private Integer totalTimes;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 上次登陆时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}