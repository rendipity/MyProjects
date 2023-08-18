package com.publicapi.apimanage.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.dao.DO.ApiUserDO;

/**
* @author chenxinyu
* @description 针对表【api_user】的数据库操作Service
* @createDate 2023-07-17 23:02:16
*/
public interface ApiUserRepository extends IService<ApiUserDO> {

    CommonPage<ApiUserDO> pageUser(UserPageQuery userPageQuery);
}
