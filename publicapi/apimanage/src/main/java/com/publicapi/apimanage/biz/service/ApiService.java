package com.publicapi.apimanage.biz.service;

import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.common.qto.ApiEvent;
import com.publicapi.apimanage.common.qto.ApiListQuery;
import com.publicapi.apimanage.common.qto.ApiPageQuery;
import com.publicapi.modal.CommonPage;

import java.util.List;

public interface ApiService {

    /**
 A   * 新增api
     */
    public String createApi(ApiResource apiResource);

    /**
     * 编辑api
     */
    public Boolean updateApi(ApiResource apiResource);

    /**
     * 根据group 查询api列表
     */
    public CommonPage<ApiResource> pageApi(ApiPageQuery apiPageQuery);

    /**
     * 获取所有可用的api列表
     */
    List<ApiResource> listApi(ApiListQuery apiListQuery);
    /**
     * api详情
     */
    public ApiResource detailsApi(String apiCode);
    /**
     * 禁用、启用Api
     */
    public Boolean eventApi(ApiEvent event);
    /**
     * 删除api
     */
    public Boolean removeApi(String apiCode);

    Boolean invokeResource(String username, String apiCode);
}
