package com.publicapi.apimanage.biz.convert;

import com.publicapi.apimanage.biz.bo.ApiParams;
import com.publicapi.apimanage.dao.DO.ApiParamsDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiParamsConvert {

    List<ApiParamsDO> listModal2Do(List<ApiParams> apiParamList);

    List<ApiParams> listDo2Modal(List<ApiParamsDO> apiParamDOList);

    ApiParamsDO modal2Do(ApiParams apiParams);

    ApiParams do2Modal(ApiParamsDO apiParamsDO);
}
