package com.publicapi.dubboclient.impl;

import com.publicapi.dubboclient.AuthenticationClient;
import com.publicapi.facade.AuthenticationFacade;
import com.publicapi.modal.Result;
import com.publicapi.modal.authentication.UserAuthDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationClientImpl implements AuthenticationClient {

    @DubboReference
    private AuthenticationFacade authenticationFacade;

    // todo 缓存用户信息

    @Override
    public Result<UserAuthDTO> getAuthByAppKey(String appKey) {
        return authenticationFacade.getAuthByAppKey(appKey);
    }

    @Override
    public Result<List<UserAuthDTO>> listAuthByAppKey() {
        return authenticationFacade.listAuthByAppKey();
    }
}
