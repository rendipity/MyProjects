package com.publicapi.facade;

import com.publicapi.modal.api.ApiResourceDTO;

import java.util.List;

public interface ApiFacade {
    List<ApiResourceDTO> listApi();
}
