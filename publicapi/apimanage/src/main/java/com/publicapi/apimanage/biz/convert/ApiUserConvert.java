package com.publicapi.apimanage.biz.convert;

import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.dao.DO.ApiUserDO;
import com.publicapi.apimanage.web.vo.user.RegisterUserVO;
import com.publicapi.apimanage.web.vo.user.UserDetailsVO;
import com.publicapi.apimanage.web.vo.user.UserKeyVO;
import com.publicapi.apimanage.web.vo.user.UserPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING )
public interface ApiUserConvert {

    ApiUser vo2Modal(RegisterUserVO userVO);

    ApiUser do2Modal(ApiUserDO userDO);

    List<ApiUser> listDo2Modal(List<ApiUserDO> userDO);

    List<UserPageVO> listModal2PageVO(List<ApiUser> users);

    UserPageVO modal2PageVO(ApiUser apiUser);

    ApiUserDO modal2DO(ApiUser user);

    UserDetailsVO modal2DetailsVo(ApiUser user);


    UserKeyVO modal2KeyVO(ApiUser user);
}
