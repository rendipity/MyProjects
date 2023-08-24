package com.publicapi.apimanage.biz.service;


import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.web.vo.user.*;
import com.publicapi.modal.CommonPage;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * 发送注册用户验证码 注册时使用
     * @param phone 手机号
     * @param ip ip
     * @return 发送结果
     */
    Boolean sendRegisterAuthCode(String phone, String ip);

    /**
     * 发送敏感信息验证码  查看和修改appkey时使用
     * @return
     */
    public Boolean sendSensitiveAuthCode();

    /**
     * 注册
     * @param registerUserVO
     * @return
     */
    Boolean register(RegisterUserVO registerUserVO);

    /**
     * 登陆
     * @param loginUserVO
     * @return
     */
    String login(LoginUserVO loginUserVO);

    /**
     * 修改密码
     * @param updatePasswordVO
     * @return
     */
    Boolean updatePassword(UpdatePasswordVO updatePasswordVO);

    /**
     * 获取当前用户详情
     */
    public UserDetailsVO getUser();

    /**
     * 编辑用户头像、昵称
     */
    public Boolean updateUserInfo(UpdateUserInfoVO updateUser);
    /**
     * 修改角色
     */
    public Boolean updateRole(UpdateRoleVO updateRoleVO);

    /**
     *  用户列表
     */
    public CommonPage<UserPageVO> userList(UserPageQuery userPageQuery);
    /**
     * 查看appKey、secretKey
     */
    public UserKeyVO getUserKey(String authCode);

    /**
     * 重置appKey、secretKey
     * */
    public UserKeyVO resetUserKey(String authCode);

}
