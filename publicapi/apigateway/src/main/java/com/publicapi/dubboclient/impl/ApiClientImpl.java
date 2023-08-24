package com.publicapi.dubboclient.impl;


import com.publicapi.dubboclient.ApiClient;
import com.publicapi.facade.ApiFacade;
import com.publicapi.modal.Result;
import com.publicapi.modal.api.ApiResourceDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiClientImpl implements ApiClient {


    @DubboReference
    ApiFacade apiFacade;

    @Override
    public Result<List<ApiResourceDTO>> listApiResource() {
        return apiFacade.listApi();
    }
}
