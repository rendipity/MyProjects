package com.publicapi.apimanage.biz.facadeimpl;

import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.biz.convert.ApiResourceConvert;
import com.publicapi.apimanage.biz.service.ApiService;
import com.publicapi.apimanage.common.qto.ApiListQuery;
import com.publicapi.facade.ApiFacade;
import com.publicapi.modal.Result;
import com.publicapi.modal.api.ApiResourceDTO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

import static com.publicapi.constants.APIConstants.ENABLE;


@DubboService
public class ApiFacadeImpl implements ApiFacade {
    @Resource
    private ApiService apiService;

    @Resource
    private ApiResourceConvert apiConvert;

    @Override
    public Result<List<ApiResourceDTO>> listApi() {
        ApiListQuery listQuery = new ApiListQuery();
        listQuery.setStatus(ENABLE);
        List<ApiResource> apiResources = apiService.listApi(listQuery);
        return Result.success(apiConvert.listModal2Dto(apiResources));
    }

    @Override
    public Result<Boolean> invokeResource(String username, String apiCode) {
        return Result.success(apiService.invokeResource(username,apiCode));
    }
}
