package com.publicapi.apimanage.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicapi.apimanage.dao.DO.ApiDictionaryDO;
import com.publicapi.apimanage.dao.mapper.ApiDictionaryMapper;
import com.publicapi.apimanage.dao.repository.ApiDictionaryRepository;
import org.springframework.stereotype.Service;

/**
* @author chenxinyu
* @description 针对表【api_dictionary】的数据库操作Service实现
* @createDate 2023-07-05 23:19:02
*/
@Service
public class ApiDictionaryRepositoryImpl extends ServiceImpl<ApiDictionaryMapper, ApiDictionaryDO>
    implements ApiDictionaryRepository {

}




