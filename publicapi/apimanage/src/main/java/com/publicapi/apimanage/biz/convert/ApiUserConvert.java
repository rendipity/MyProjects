package com.publicapi.apimanage.biz.convert;

import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.dao.DO.ApiUserDO;
import com.publicapi.apimanage.web.vo.user.RegisterUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING )
public interface ApiUserConvert {

    ApiUser vo2Modal(RegisterUserVO userVO);

    ApiUser do2Modal(ApiUserDO userDO);

    ApiUserDO modal2DO(ApiUser user);
}
