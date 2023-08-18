package com.publicapi.apimanage.web.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName api_user
 */
@Data
public class UserDetailsVO implements Serializable {
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
     * 头像
     */
    private String headPhoto;

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