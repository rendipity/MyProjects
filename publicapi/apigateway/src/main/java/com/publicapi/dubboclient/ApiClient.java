package com.publicapi.dubboclient;

import com.publicapi.modal.Result;
import com.publicapi.modal.api.ApiResourceDTO;

import java.util.List;

public interface ApiClient {

    Result<List<ApiResourceDTO>> listApiResource();


    Result<Boolean> invokeResource(String username, String apiCode);
}
