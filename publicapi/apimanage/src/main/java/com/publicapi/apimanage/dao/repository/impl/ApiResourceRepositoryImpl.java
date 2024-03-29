package com.publicapi.apimanage.dao.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.dao.DO.ApiResourceDO;
import com.publicapi.apimanage.dao.mapper.ApiResourceMapper;
import com.publicapi.apimanage.dao.repository.ApiResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chenxinyu
* @description 针对表【api_resource】的数据库操作Service实现
* @createDate 2023-07-05 23:20:58
*/
@Service
public class ApiResourceRepositoryImpl extends ServiceImpl<ApiResourceMapper, ApiResourceDO>
    implements ApiResourceRepository {
    @Override
    public ApiResourceDO getByCode(String code) {
        return getOne(Wrappers.<ApiResourceDO>lambdaQuery().eq(ApiResourceDO::getCode,code));
    }

    @Override
    public ApiResourceDO getOne(String protocol, String host, String path) {
        return getOne(Wrappers.<ApiResourceDO>lambdaQuery()
                .eq(ApiResourceDO::getHost,host)
                .eq(ApiResourceDO::getPath,path)
                .eq(ApiResourceDO::getProtocol,protocol)
                );
    }

    @Override
    public List<ApiResourceDO> listApi(String status) {
        List<ApiResourceDO> resourceDOS = list(Wrappers.<ApiResourceDO>lambdaQuery()
                .eq(StrUtil.isNotBlank(status), ApiResourceDO::getStatus, status));
        return resourceDOS;
    }
}




