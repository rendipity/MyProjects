package com.publicapi.apimanage.core.service.user.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.biz.convert.ApiUserConvert;
import com.publicapi.apimanage.common.query.UserListQuery;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.core.service.user.UserDomainService;
import com.publicapi.apimanage.dao.DO.ApiUserDO;
import com.publicapi.apimanage.dao.repository.ApiUserRepository;
import com.publicapi.modal.CommonPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserDomainServiceImpl implements UserDomainService {

    @Resource
    private ApiUserRepository userRepository;

    @Resource
    private ApiUserConvert userConvert;

    @Override
    public Boolean createUser(ApiUser user) {
        return userRepository.save(userConvert.modal2DO(user));
    }

    @Override
    public ApiUser getUserByPhone(String phone) {
        ApiUserDO apiUserDO = userRepository.getOne(Wrappers.<ApiUserDO>lambdaQuery().eq(ApiUserDO::getPhone, phone));
        return userConvert.do2Modal(apiUserDO);
    }

    @Override
    public ApiUser getUserByUsername(String username) {
        ApiUserDO apiUserDO = userRepository.getOne(Wrappers.<ApiUserDO>lambdaQuery().eq(ApiUserDO::getUsername, username));
        return userConvert.do2Modal(apiUserDO);
    }

    @Override
    public ApiUser getUserByAppKey(String appKey) {
        ApiUserDO apiUserDO = userRepository.getOne(Wrappers.<ApiUserDO>lambdaQuery().eq(ApiUserDO::getAppKey, appKey));
        return userConvert.do2Modal(apiUserDO);
    }

    @Override
    public Boolean updateUserById(ApiUser user) {
        return userRepository.updateById(userConvert.modal2DO(user));
    }

    @Override
    public CommonPage<ApiUser> pageUser(UserPageQuery pageQuery) {
        CommonPage<ApiUserDO> userDOCommonPage = userRepository.pageUser(pageQuery);
        return CommonPage.convert(userDOCommonPage,userConvert.listDo2Modal(userDOCommonPage.getLists()));
    }

    @Override
    public List<ApiUser> listAllUser(UserListQuery listQuery) {
        List<ApiUserDO> userDOS = userRepository.listUser(listQuery);
        return userConvert.listDo2Modal(userDOS);
    }
}
