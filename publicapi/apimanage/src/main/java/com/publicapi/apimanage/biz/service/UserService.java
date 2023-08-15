package com.publicapi.apimanage.biz.service;


import com.publicapi.apimanage.web.vo.user.LoginUserVO;
import com.publicapi.apimanage.web.vo.user.RegisterUserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
     * @param phone
     * @param ip
     * @return
     */
    public Boolean sendSensitiveAuthCode(String phone, String ip);

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
    Boolean login(@RequestBody LoginUserVO loginUserVO);

}
