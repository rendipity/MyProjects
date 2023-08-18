package com.publicapi.apimanage.web.controller;

import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.web.vo.user.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 发送验证码
     * 同一个手机号、同一个ip，1分钟内只能发送一次
     * 验证码5分钟内有效，支持5分钟内的多个验证码都有效
     */
    @GetMapping("/authCode")
    public Result<Boolean> authCode(String phone){
        // todo  获取用户ip
        String ip = "127.0.0.1";
        return Result.success(userService.sendRegisterAuthCode(phone,ip));
    }
    // 注册
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody @Validated RegisterUserVO registerUserVO){
        return Result.success(userService.register(registerUserVO));
    }

    // 登陆
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginUserVO loginUserVO){
        return Result.success(userService.login(loginUserVO));
    }

    //修改密码
    @PostMapping("/password/update")
    public Result<Boolean> updatePassword(@RequestBody @Validated UpdatePasswordVO updatePasswordVO){
        return Result.success(userService.updatePassword(updatePasswordVO));
    }

    // 获取当前用户详情
    @GetMapping("/details")
    public Result<UserDetailsVO> getUser(){
        return Result.success(userService.getUser());
    }

    // 编辑用户信息 头像，用户名
    @PostMapping("/userinfo/update")
    public Result<Boolean> updateUserInfo(@RequestBody @Validated UpdateUserInfoVO updateUser){
            return Result.success(userService.updateUserInfo(updateUser));
    }
    // 修改角色
    @PostMapping("/role/update")
    public Result<Boolean> updateRole(@RequestBody @Validated UpdateRoleVO updateRoleVO){
        return Result.success(userService.updateRole(updateRoleVO));
    }

    // 用户列表
    @GetMapping("/list")
    public Result<CommonPage<UserPageVO>> userList(UserPageQuery userPageQuery){
        return Result.success(userService.userList(userPageQuery));
    }

    /**
     * 发送敏感信息验证码
     */
    @GetMapping("/sensitive")
    public Result<Boolean> sensitiveAuthCode(){
        return Result.success(userService.sendSensitiveAuthCode());
    }

    /**
     * 查看appKey、secretKey
     */
    @GetMapping("/key")
    public Result<UserKeyVO> getUserKey(String authCode){
        return Result.success(userService.getUserKey(authCode));
    }

    /**
     * 重置appKey、secretKey
     */
    @GetMapping
    public Result<UserKeyVO> resetUserKey(String authCode){
        return Result.success(userService.resetUserKey(authCode));
    }

}
