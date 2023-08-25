package com.publicapi.apimanage.dao.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.dao.DO.InvokeInfoDO;
import com.publicapi.apimanage.dao.repository.InvokeInfoRepository;
import com.publicapi.apimanage.dao.mapper.InvokeInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author chenxinyu
* @description 针对表【invoke_info】的数据库操作Service实现
* @createDate 2023-08-25 17:08:01
*/
@Service
public class InvokeInfoRepositoryImpl extends ServiceImpl<InvokeInfoMapper, InvokeInfoDO>
    implements InvokeInfoRepository {

    @Override
    public InvokeInfoDO getOne(String username) {
        return getOne(Wrappers.<InvokeInfoDO>lambdaQuery().eq(InvokeInfoDO::getUsername,username));
    }
}




