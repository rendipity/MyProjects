package com.publicapi.apimanage.biz.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.publicapi.apimanage.biz.bo.ApiParams;
import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.biz.convert.ApiParamsConvert;
import com.publicapi.apimanage.biz.convert.ApiResourceConvert;
import com.publicapi.apimanage.biz.service.ApiService;
import com.publicapi.apimanage.biz.statemachine.ApiStateMachine;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.exception.ApiResourceException;
import com.publicapi.apimanage.common.qto.ApiEvent;
import com.publicapi.apimanage.common.qto.ApiListQuery;
import com.publicapi.apimanage.common.qto.ApiPageQuery;
import com.publicapi.apimanage.common.utils.CodeUtil;
import com.publicapi.apimanage.core.service.mq.MqService;
import com.publicapi.apimanage.dao.DO.ApiParamsDO;
import com.publicapi.apimanage.dao.DO.ApiResourceDO;
import com.publicapi.apimanage.dao.repository.ApiParamsRepository;
import com.publicapi.apimanage.dao.repository.ApiResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.publicapi.apimanage.common.enums.ErrorResultEnum.*;
import static com.publicapi.constants.APIConstants.REQUEST;
import static com.publicapi.constants.APIConstants.RESPONSE;
import static com.publicapi.modal.mq.RabbitMqConstants.*;

@Service
public class ApiServiceImpl implements ApiService {

    private final String CREATOR = "lijie";

    @Resource
    private ApiResourceConvert apiResourceConvert;
    @Resource
    private ApiParamsConvert paramsConvert;

    @Resource
    private ApiResourceRepository apiResourceRepository;

    @Resource
    private ApiParamsRepository paramsRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private ApiStateMachine stateMachine;

    @Resource
    private MqService mqService;

    @Override
    public String createApi(ApiResource apiResource) {
        // 判断url是否重复
        checkUrl(apiResource);
        // todo 校验该api是否可以调用成功
        String code = generateCode();
        apiResource.setCode(code);
        apiResource.setCreateTime(new Date());
        // todo creator
        apiResource.setCreator(CREATOR);
        // 合并请求参数和响应参数
        List<ApiParams> requestParams = apiResource.getRequestParams();
        List<ApiParamsDO> requestParamsDOS = paramsConvert.listModal2Do(requestParams);
        for (ApiParamsDO param: requestParamsDOS){
            param.setApiCode(code);
            param.setType(REQUEST);
        }
        List<ApiParams> responseParams = apiResource.getResponseParams();
        List<ApiParamsDO> responseParamsDOS = paramsConvert.listModal2Do(responseParams);
        for (ApiParamsDO param: responseParamsDOS){
            param.setApiCode(code);
            param.setType(RESPONSE);
        }
        // 合并两个list
        List<ApiParamsDO> params = Stream.concat(requestParamsDOS.stream(), responseParamsDOS.stream()).collect(Collectors.toList());
        transactionTemplate.executeWithoutResult(status->{
            ApiResourceDO apiResourceDO = apiResourceConvert.modal2Do(apiResource);
            // 插入 apiResource
            apiResourceRepository.save(apiResourceDO);
            // 批量插入 apiParams
            paramsRepository.saveBatch(params);
        });
        // 发送消息
        mqService.sendMqMessage(ADD_ROUTE_MESSAGE,apiResourceConvert.modal2Dto(apiResource));
        return code;
    }

    @Override
    public Boolean updateApi(ApiResource apiResource) {
        // 校验该api是否存在
        ApiResourceDO apiResourceDO = apiResourceRepository.getOne(Wrappers.<ApiResourceDO>lambdaQuery()
                .eq(ApiResourceDO::getCode, apiResource.getCode()));
        if (apiResourceDO == null)
            throw new ApiResourceException(API_NOT_EXIST);
        // 合并请求参数和响应参数
        List<ApiParams> requestParams = apiResource.getRequestParams();
        List<ApiParamsDO> requestParamsDOS = paramsConvert.listModal2Do(requestParams);
        for (ApiParamsDO param: requestParamsDOS){
            param.setApiCode(apiResource.getCode());
            param.setType(REQUEST);
        }
        List<ApiParams> responseParams = apiResource.getResponseParams();
        List<ApiParamsDO> responseParamsDOS = paramsConvert.listModal2Do(responseParams);
        for (ApiParamsDO param: responseParamsDOS){
            param.setApiCode(apiResource.getCode());
            param.setType(RESPONSE);
        }
        // 合并两个list
        List<ApiParamsDO> params = Stream.concat(requestParamsDOS.stream(), responseParamsDOS.stream()).collect(Collectors.toList());
        transactionTemplate.executeWithoutResult(status->{
            // 更新apiResource
            apiResourceRepository.update(apiResourceConvert.modal2Do(apiResource),
                    Wrappers.<ApiResourceDO>lambdaQuery().eq(ApiResourceDO::getCode,apiResource.getCode()));
            // 删除原来的apiParams
            paramsRepository.removeParams(apiResource.getCode());
            //增加新的params
            paramsRepository.saveBatch(params);
        });
        mqService.sendMqMessage(UPDATE_ROUTE_MESSAGE,apiResourceConvert.modal2Dto(apiResource));
        return true;
    }

    @Override
    public CommonPage<ApiResource> pageApi(ApiPageQuery apiPageQuery) {
        Integer pageNum = apiPageQuery.getPageNum();
        Integer pageSize = apiPageQuery.getPageSize();
        Page<ApiResourceDO> pageResult = apiResourceRepository.page(new Page<>(pageNum, pageSize),
                Wrappers.<ApiResourceDO>lambdaQuery()
                        .eq(StrUtil.isNotBlank(apiPageQuery.getGroupCode()),ApiResourceDO::getGroupCode, apiPageQuery.getGroupCode()));
        return CommonPage.build(pageNum,pageSize,pageResult.getPages(),pageResult.getTotal(),apiResourceConvert.listDo2modal(pageResult.getRecords()));
    }

    @Override
    public List<ApiResource> listApi(ApiListQuery apiListQuery) {
        List<ApiResourceDO> apiResourceDOS = apiResourceRepository.listApi(apiListQuery.getStatus());
        return apiResourceConvert.listDo2modal(apiResourceDOS);
    }

    @Override
    public ApiResource detailsApi(String apiCode) {
        ApiResourceDO apiResourceDO = apiResourceRepository.getOne(Wrappers.<ApiResourceDO>lambdaQuery().eq(ApiResourceDO::getCode, apiCode));
        List<ApiParamsDO> params = paramsRepository.list(Wrappers.<ApiParamsDO>lambdaQuery().eq(ApiParamsDO::getApiCode, apiCode));
        ApiResource apiResource = apiResourceConvert.do2Modal(apiResourceDO);
        // 拆分param为requestParams和responseParams
        Map<String, List<ApiParamsDO>> paramsGroup = params.stream().collect(Collectors.groupingBy(ApiParamsDO::getType));
        apiResource.setRequestParams(paramsConvert.listDo2Modal(paramsGroup.get(REQUEST)));
        apiResource.setResponseParams(paramsConvert.listDo2Modal(paramsGroup.get(RESPONSE)));
        return apiResource;
    }

    @Override
    public Boolean eventApi(ApiEvent event) {
        String nextStatus = stateMachine.transfer(event.getStatus(), event.getEvent());
        Assert.notNull(nextStatus,()->new ApiResourceException(PARAMETER_EXCEPTION));
        ApiResourceDO apiResourceDO = apiResourceRepository.getByCode(event.getCode());
        Assert.notNull(apiResourceDO,()->new ApiResourceException(API_NOT_EXIST));
        Assert.equals(apiResourceDO.getStatus(),event.getStatus(),()->new ApiResourceException(ABNORMAL_STATE));
        boolean updateResult = apiResourceRepository.update(
                Wrappers.<ApiResourceDO>lambdaUpdate()
                        .eq(ApiResourceDO::getCode, event.getCode())
                        .eq(ApiResourceDO::getStatus, event.getStatus())
                        .set(ApiResourceDO::getStatus, nextStatus)
        );
        ApiResource apiResource = apiResourceConvert.do2Modal(apiResourceDO);
        // todo 并发情况下是否有问题
        apiResource.setStatus(nextStatus);
        mqService.sendMqMessage(STATUS_CHANGED_ROUTE_MESSAGE, apiResourceConvert.modal2Dto(apiResource));
        return updateResult;
    }

    @Override
    public Boolean removeApi(String apiCode) {
        transactionTemplate.executeWithoutResult(status->{
            apiResourceRepository.remove(Wrappers.<ApiResourceDO>lambdaQuery()
                    .eq(ApiResourceDO::getCode,apiCode));
            paramsRepository.removeParams(apiCode);
        });
        mqService.sendMqMessage(REMOVE_ROUTE_MESSAGE,apiCode);
        return true;
    }
    private String generateCode(){
        String code = CodeUtil.generate();
        ApiResourceDO apiResourceDO = apiResourceRepository.getByCode(code);
        while(apiResourceDO != null){
            code = CodeUtil.generate();
            apiResourceDO = apiResourceRepository.getByCode(code);
        }
        return code;
    }
    private void checkUrl(ApiResource apiResource){
        ApiResourceDO api = apiResourceRepository.getOne(apiResource.getProtocol(), apiResource.getHost(), apiResource.getPath());
        if (ObjectUtil.isNotNull(api))
            throw new ApiResourceException(URL_DUPLICATION);
    }
}
