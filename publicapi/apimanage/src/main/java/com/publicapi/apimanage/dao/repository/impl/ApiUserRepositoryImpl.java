package com.publicapi.apimanage.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.dao.DO.ApiUserDO;
import com.publicapi.apimanage.dao.mapper.ApiUserMapper;
import com.publicapi.apimanage.dao.repository.ApiUserRepository;
import org.springframework.stereotype.Service;

/**
* @author chenxinyu
* @description 针对表【api_user】的数据库操作Service实现
* @createDate 2023-07-17 23:02:16
*/
@Service
public class ApiUserRepositoryImpl extends ServiceImpl<ApiUserMapper, ApiUserDO>
    implements ApiUserRepository {

}




