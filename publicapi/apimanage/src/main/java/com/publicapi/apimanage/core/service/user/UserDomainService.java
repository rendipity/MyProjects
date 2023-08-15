package com.publicapi.apimanage.core.service.user;


import com.publicapi.apimanage.biz.bo.ApiUser;

public interface UserDomainService {

    Boolean createUser(ApiUser user);

    ApiUser getUserByPhone(String phone);

    ApiUser getUserByUsername(String username);
}
