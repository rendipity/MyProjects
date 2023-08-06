package com.publicapi.apimanage.biz.service.impl;

import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.service.message.MessageService;
import com.publicapi.apimanage.core.template.AuthCodeTemplateParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    MessageService messageService;

    @Override
    public Boolean sendRegisterAuthCode(String phone) {
        // 生成验证码
        String code = "1111";
        //发送消息
        try {
            return messageService.sendMsg(phone, MessageEnum.REGISTER_AUTH_CODE,new AuthCodeTemplateParam(code));
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("发送短信验证码失败 phone={},code={}",phone,code);
            return false;
        }
    }

    public Boolean sendSensitiveAuthCode(String phone) {
        // 生成验证码
        String code = "2222";
        //发送消息
        try {
            return messageService.sendMsg(phone, MessageEnum.SENSITIVE_AUTH_CODE,new AuthCodeTemplateParam(code));
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("发送短信验证码失败 phone={},code={}",phone,code);
            return false;
        }
    }
}
