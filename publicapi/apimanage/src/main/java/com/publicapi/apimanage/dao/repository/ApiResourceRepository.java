package com.publicapi.apimanage.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.publicapi.apimanage.dao.DO.ApiResourceDO;

import java.util.List;

/**
* @author chenxinyu
* @description 针对表【api_resource】的数据库操作Service
* @createDate 2023-07-05 23:20:58
*/
public interface ApiResourceRepository extends IService<ApiResourceDO> {

    ApiResourceDO getByCode(String code);

    ApiResourceDO getOne(String protocol, String host, String path);

    List<ApiResourceDO> listApi(String status);
}
