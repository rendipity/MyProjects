package com.publicapi.apimanage.biz.service;


import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Boolean sendRegisterAuthCode(String phone);

    public Boolean sendSensitiveAuthCode(String phone);

}
