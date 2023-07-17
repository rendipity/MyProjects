package com.publicapi.apimanage.web.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class UserListVO {
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
     * 调用次数
     */
    private Integer invokeTimes;

    /**
     * 总的调用次数
     */
    private Integer totalTimes;

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
