package com.publicapi.facade;

import com.publicapi.modal.Result;
import com.publicapi.modal.authentication.UserAuthDTO;

import java.util.List;

public interface AuthenticationFacade {

    /**
     * 通过appKey 获取用户签名信息
     * @param appKey
     * @return
     */
    Result<UserAuthDTO> getAuthByAppKey(String appKey);

    /**
     * 获取所有的用户签名信息
     * @return
     */
    Result<List<UserAuthDTO>> listAuthByAppKey();
}
