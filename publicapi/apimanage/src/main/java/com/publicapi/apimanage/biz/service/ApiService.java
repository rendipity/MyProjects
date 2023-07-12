package com.publicapi.apimanage.biz.service;

import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.qto.ApiEvent;
import com.publicapi.apimanage.common.qto.ApiPageQuery;

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
}
