package com.publicapi.dynamicroute.service.dynamicrote.convert;

import com.publicapi.dynamicroute.apimodal.ApiResource;
import com.publicapi.modal.api.ApiResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiResourceConvert {
    ApiResource dto2modal (ApiResourceDTO apiDTO);
}
