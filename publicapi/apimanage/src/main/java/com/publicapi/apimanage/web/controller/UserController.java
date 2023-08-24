package com.publicapi.apimanage.web.controller;

import cn.hutool.core.util.StrUtil;
import com.publicapi.apimanage.biz.constants.RoleEnum;
import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.web.exception.AccessControl;
import com.publicapi.apimanage.web.vo.user.*;
import com.publicapi.exception.ApiManageException;
import com.publicapi.modal.CommonPage;
import com.publicapi.modal.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import static com.publicapi.enums.ErrorResultEnum.AUTH_CODE_IS_EMPTY;

@RestController
@RequestMapping("/user")
@Validated
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
    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody @Validated RegisterUserVO registerUserVO){
        return Result.success(userService.register(registerUserVO));
    }

    /**
     * 登陆
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginUserVO loginUserVO){
        return Result.success(userService.login(loginUserVO));
    }

    /**
     * 修改密码
     */
    @PostMapping("/password/update")
    public Result<Boolean> updatePassword(@RequestBody @Validated UpdatePasswordVO updatePasswordVO){
        return Result.success(userService.updatePassword(updatePasswordVO));
    }

    /**
     *  获取当前用户详情
     */

    @GetMapping("/details")
    public Result<UserDetailsVO> getUser(){
        return Result.success(userService.getUser());
    }

    /**
     *  编辑用户头像，用户名
     */
    @PostMapping("/userinfo/update")
    public Result<Boolean> updateUserInfo(@RequestBody @Validated UpdateUserInfoVO updateUser){
            return Result.success(userService.updateUserInfo(updateUser));
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
    @GetMapping("/authentication")
    public Result<UserKeyVO> getUserKey(@NotEmpty(message = "[authCode]验证码不能为空") String authCode){
        if (StrUtil.isEmpty(authCode)){
            throw new ApiManageException(AUTH_CODE_IS_EMPTY);
        }
        return Result.success(userService.getUserKey(authCode));
    }

    /**
     * 重置appKey、secretKey
     */
    @GetMapping("/authentication/reset")
    public Result<UserKeyVO> resetUserKey(@NotEmpty(message = "[authCode]验证码不能为空") String authCode){
        if (StrUtil.isEmpty(authCode)){
            throw new ApiManageException(AUTH_CODE_IS_EMPTY);
        }
        return Result.success(userService.resetUserKey(authCode));
    }

    // 管理员接口

    /**
     * 修改角色
     */
    @PostMapping("/role/update")
    @AccessControl(RoleEnum.ADMIN)
    public Result<Boolean> updateRole(@RequestBody @Validated UpdateRoleVO updateRoleVO){
        return Result.success(userService.updateRole(updateRoleVO));
    }

    /**
     * 用户列表
     */
    @GetMapping("/list")
    @AccessControl(RoleEnum.ADMIN)
    public Result<CommonPage<UserPageVO>> userList(UserPageQuery userPageQuery){
        return Result.success(userService.userList(userPageQuery));
    }

}
