package com.publicapi.apimanage.biz.convert;

import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.dao.DO.ApiResourceDO;
import com.publicapi.apimanage.web.vo.api.AddApiVO;
import com.publicapi.apimanage.web.vo.api.DetailsApiVO;
import com.publicapi.apimanage.web.vo.api.ListApiVO;
import com.publicapi.apimanage.web.vo.api.UpdateApiVO;
import com.publicapi.modal.ApiResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(uses = ApiParamsConvert.class, componentModel = MappingConstants.ComponentModel.SPRING )
public interface ApiResourceConvert {

    ApiResource addVo2modal(AddApiVO addApiVO);

    ApiResourceDO modal2Do(ApiResource apiResource);

    ApiResource do2Modal(ApiResourceDO apiResource);

    ApiResource updateVo2modal(UpdateApiVO updateApiVO);

    List<ApiResource> listDo2modal(List<ApiResourceDO> apiResourceDOS);

    List<ListApiVO> listModal2Vo(List<ApiResource> apiResources);

    ListApiVO modal2Vo(ApiResource apiResource);

    DetailsApiVO modal2DetailsVO(ApiResource apiResource);

    List<ApiResourceDTO> listModal2Dto(List<ApiResource> apiList);
    ApiResourceDTO modal2Dto(ApiResource apiResource);
}
