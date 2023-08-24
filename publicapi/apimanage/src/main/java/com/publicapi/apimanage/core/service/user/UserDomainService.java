package com.publicapi.apimanage.core.service.user;


import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.common.query.UserListQuery;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.modal.CommonPage;

import java.util.List;

public interface UserDomainService {

    Boolean createUser(ApiUser user);

    ApiUser getUserByPhone(String phone);

    ApiUser getUserByUsername(String username);

    ApiUser getUserByAppKey(String appKey);

    Boolean updateUserById(ApiUser user);

    CommonPage<ApiUser> pageUser(UserPageQuery pageQuery);

    List<ApiUser> listAllUser(UserListQuery listQuery);
}
