package com.publicapi.apimanage.core.service.user;


import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.query.UserPageQuery;

import java.util.List;

public interface UserDomainService {

    Boolean createUser(ApiUser user);

    ApiUser getUserByPhone(String phone);

    ApiUser getUserByUsername(String username);

    Boolean updateUserById(ApiUser user);

    CommonPage<ApiUser> pageUser(UserPageQuery pageQuery);
}
