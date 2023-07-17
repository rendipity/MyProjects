package com.publicapi.apimanage.web.vo.user;

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
     * 剩余调用次数
     */
    private Integer restInvokeTimes;

    /**
     * 创建时间
     */
    private Date createTime;
}