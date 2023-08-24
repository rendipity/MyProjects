package com.publicapi.apimanage.dao.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.dao.DO.ApiParamsDO;
import com.publicapi.apimanage.dao.mapper.ApiParamsMapper;
import com.publicapi.apimanage.dao.repository.ApiParamsRepository;
import org.springframework.stereotype.Service;

/**
* @author chenxinyu
* @description 针对表【api_params】的数据库操作Service实现
* @createDate 2023-07-05 23:20:26
*/
@Service
public class ApiParamsRepositoryImpl extends ServiceImpl<ApiParamsMapper, ApiParamsDO>
    implements ApiParamsRepository {

    @Override
    public void removeParams(String apiCode) {
        remove(Wrappers.<ApiParamsDO>lambdaQuery().eq(ApiParamsDO::getApiCode,apiCode));
    }
}




