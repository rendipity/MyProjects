package com.publicapi.apimanage.dao.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.dao.DO.ApiResourceDO;
import com.publicapi.apimanage.dao.repository.ApiResourceRepository;
import com.publicapi.apimanage.dao.mapper.ApiResourceMapper;
import org.springframework.stereotype.Service;

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
}




