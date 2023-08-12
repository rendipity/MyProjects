package com.publicapi.dynamicroute.dubboclient.impl;


import com.publicapi.dynamicroute.dubboclient.ApiClient;
import com.publicapi.facade.ApiFacade;
import com.publicapi.modal.api.ApiResourceDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiClientImpl implements ApiClient {


    @DubboReference
    ApiFacade apiFacade;

    @Override
    public List<ApiResourceDTO> listApiResource() {
        return apiFacade.listApi();
    }
}
