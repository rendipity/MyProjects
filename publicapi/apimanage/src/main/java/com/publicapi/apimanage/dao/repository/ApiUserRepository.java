package com.publicapi.apimanage.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.publicapi.apimanage.common.query.UserListQuery;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.dao.DO.ApiUserDO;
import com.publicapi.modal.CommonPage;

import java.util.List;

/**
* @author chenxinyu
* @description 针对表【api_user】的数据库操作Service
* @createDate 2023-07-17 23:02:16
*/
public interface ApiUserRepository extends IService<ApiUserDO> {

    CommonPage<ApiUserDO> pageUser(UserPageQuery userPageQuery);

    List<ApiUserDO> listUser(UserListQuery userListQuery);
}
