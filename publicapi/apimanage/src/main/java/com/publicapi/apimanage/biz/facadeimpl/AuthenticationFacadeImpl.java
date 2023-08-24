package com.publicapi.apimanage.biz.facadeimpl;

import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.biz.convert.ApiUserConvert;
import com.publicapi.apimanage.common.query.UserListQuery;
import com.publicapi.apimanage.core.service.user.UserDomainService;
import com.publicapi.facade.AuthenticationFacade;
import com.publicapi.modal.Result;
import com.publicapi.modal.authentication.UserAuthDTO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private ApiUserConvert userConvert;

    @Override
    public Result<UserAuthDTO> getAuthByAppKey(String appKey) {
        ApiUser apiUser = userDomainService.getUserByAppKey(appKey);
        UserAuthDTO userAuthDTO = userConvert.modal2UserAuthDto(apiUser);
        return Result.success(userAuthDTO);
    }

    @Override
    public Result<List<UserAuthDTO>> listAuthByAppKey() {
        // 没有查询条件 全量查询
        List<ApiUser> apiUsers = userDomainService.listAllUser(new UserListQuery());
        return Result.success(userConvert.listModal2UserAuthDto(apiUsers));
    }
}
