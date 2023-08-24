package com.publicapi.apimanage.dao.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.common.query.UserListQuery;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.dao.DO.ApiUserDO;
import com.publicapi.apimanage.dao.mapper.ApiUserMapper;
import com.publicapi.apimanage.dao.repository.ApiUserRepository;
import com.publicapi.modal.CommonPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chenxinyu
* @description 针对表【api_user】的数据库操作Service实现
* @createDate 2023-07-17 23:02:16
*/
@Service
public class ApiUserRepositoryImpl extends ServiceImpl<ApiUserMapper, ApiUserDO>
    implements ApiUserRepository {

    @Override
    public CommonPage<ApiUserDO> pageUser(UserPageQuery userPageQuery) {
        Page<ApiUserDO> userDOPage = page(new Page<>(userPageQuery.getPageNum(), userPageQuery.getPageSize()),
                Wrappers.<ApiUserDO>lambdaQuery()
                        .like(StrUtil.isNotBlank(userPageQuery.getUsername()), ApiUserDO::getUsername, userPageQuery.getUsername())
                        .like(StrUtil.isNotBlank(userPageQuery.getPhone()), ApiUserDO::getPhone, userPageQuery.getPhone())
                        .eq(StrUtil.isNotBlank(userPageQuery.getRole()), ApiUserDO::getRole, userPageQuery.getRole())
                        .eq(StrUtil.isNotBlank(userPageQuery.getStatus()), ApiUserDO::getStatus, userPageQuery.getStatus()));
        return CommonPage.build(userPageQuery.getPageNum(), userPageQuery.getPageSize(), userDOPage.getPages(),userDOPage.getTotal(),userDOPage.getRecords());
    }

    @Override
    public List<ApiUserDO> listUser(UserListQuery userListQuery) {
        return list(Wrappers.<ApiUserDO>lambdaQuery()
                .like(StrUtil.isNotBlank(userListQuery.getUsername()), ApiUserDO::getUsername, userListQuery.getUsername())
                .like(StrUtil.isNotBlank(userListQuery.getPhone()), ApiUserDO::getPhone, userListQuery.getPhone())
                .eq(StrUtil.isNotBlank(userListQuery.getRole()), ApiUserDO::getRole, userListQuery.getRole())
                .eq(StrUtil.isNotBlank(userListQuery.getStatus()), ApiUserDO::getStatus, userListQuery.getStatus()));
    }
}




