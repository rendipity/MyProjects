package com.publicapi.apimanage.web.controller;

import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.web.vo.user.*;
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
    public Result<Boolean> register(@RequestBody RegisterUserVO registerUserVO){
        return Result.success(userService.register(registerUserVO));
    }

    // 登陆
    @PostMapping("/login")
    public Result<Boolean> login(@RequestBody LoginUserVO loginUserVO){
        return null;
    }

    //修改密码
    @PostMapping("/password/update")
    public Result<Boolean> updatePassword(@RequestBody UpdatePasswordVO updatePasswordVO){
        return null;
    }

    // 获取当前用户详情
    @GetMapping("/details")
    public Result<UserDetailsVO> getUser(){
        return null;
    }

    // 编辑用户信息 头像，用户名
    @PostMapping("/userinfo/update")
    public Result<UserDetailsVO> updateUserInfo(@RequestBody UpdateUserInfoVO updateUser){
            return null;
    }
    // 修改角色
    @PostMapping("/role/update")
    public Result<Boolean> updateRole(@RequestBody UpdateRoleVO updateRoleVO){
        return null;
    }

    // 用户列表
    @GetMapping("/list")
    public Result<List<UserListVO>> userList(Integer pageNum, Integer pageSize){
        return null;
    }

    // 查看appKey、secretKey
    @GetMapping("/key")
    public Result<UserKeyVO> getUserKey(){
        return null;
    }

    // 重置appKey、secretKey
    @GetMapping
    public Result<UserKeyVO> resetUserKey(){
        return null;
    }

}
