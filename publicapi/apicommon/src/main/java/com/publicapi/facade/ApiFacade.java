package com.publicapi.facade;

import com.publicapi.modal.Result;
import com.publicapi.modal.api.ApiResourceDTO;

import java.util.List;

public interface ApiFacade {
    Result<List<ApiResourceDTO>> listApi();

    public Result<Boolean> invokeResource(String username, String apiCode);
}
