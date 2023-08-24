package com.publicapi.apimanage.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.publicapi.apimanage.dao.DO.ApiParamsDO;

/**
* @author chenxinyu
* @description 针对表【api_params】的数据库操作Service
* @createDate 2023-07-05 23:20:26
*/
public interface ApiParamsRepository extends IService<ApiParamsDO> {

        void removeParams(String apiCode);
}
