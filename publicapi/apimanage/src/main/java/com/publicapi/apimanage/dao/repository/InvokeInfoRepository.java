package com.publicapi.apimanage.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.publicapi.apimanage.dao.DO.InvokeInfoDO;

/**
* @author chenxinyu
* @description 针对表【invoke_info】的数据库操作Service
* @createDate 2023-08-25 17:08:01
*/
public interface InvokeInfoRepository extends IService<InvokeInfoDO> {

    InvokeInfoDO getOne(String username);

}
