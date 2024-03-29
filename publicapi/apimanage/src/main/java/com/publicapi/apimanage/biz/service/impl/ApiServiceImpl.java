package com.publicapi.apimanage.biz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
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
import com.publicapi.apimanage.common.UserContext;
import com.publicapi.apimanage.common.qto.ApiEvent;
import com.publicapi.apimanage.common.qto.ApiListQuery;
import com.publicapi.apimanage.common.qto.ApiPageQuery;
import com.publicapi.apimanage.common.utils.CodeUtil;
import com.publicapi.apimanage.common.utils.RedisCodeUtil;
import com.publicapi.apimanage.core.service.mq.MqService;
import com.publicapi.apimanage.dao.DO.ApiParamsDO;
import com.publicapi.apimanage.dao.DO.ApiResourceDO;
import com.publicapi.apimanage.dao.DO.InvokeInfoDO;
import com.publicapi.apimanage.dao.repository.ApiParamsRepository;
import com.publicapi.apimanage.dao.repository.ApiResourceRepository;
import com.publicapi.apimanage.dao.repository.InvokeInfoRepository;
import com.publicapi.exception.ApiManageException;
import com.publicapi.modal.CommonPage;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.publicapi.apimanage.biz.constants.ApiManageConstants.INVOKE_TIME_RANK_CODE;
import static com.publicapi.constants.APIConstants.REQUEST;
import static com.publicapi.constants.APIConstants.RESPONSE;
import static com.publicapi.enums.ErrorResultEnum.*;
import static com.publicapi.modal.mq.RabbitMqConstants.*;

@Service
public class ApiServiceImpl implements ApiService {

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

    @Resource
    private InvokeInfoRepository invokeInfoRepository;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public String createApi(ApiResource apiResource) {
        // 判断url是否重复
        checkUrl(apiResource);
        // todo 校验该api是否可以调用成功
        String code = generateCode();
        apiResource.setCode(code);
        apiResource.setCreateTime(new Date());
        apiResource.setCreator(UserContext.get().getUsername());
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
        // todo 开发者只能编辑自己上传的接口  管理员可以编辑所有接口
        // 校验该api是否存在
        ApiResourceDO apiResourceDO = apiResourceRepository.getOne(Wrappers.<ApiResourceDO>lambdaQuery()
                .eq(ApiResourceDO::getCode, apiResource.getCode()));
        if (apiResourceDO == null)
            throw new ApiManageException(API_NOT_EXIST);
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
        Assert.notNull(nextStatus,()->new ApiManageException(PARAMETER_EXCEPTION));
        ApiResourceDO apiResourceDO = apiResourceRepository.getByCode(event.getCode());
        Assert.notNull(apiResourceDO,()->new ApiManageException(API_NOT_EXIST));
        Assert.equals(apiResourceDO.getStatus(),event.getStatus(),()->new ApiManageException(ABNORMAL_STATE));
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
            throw new ApiManageException(URL_DUPLICATION);
    }

    @Override
    public Boolean invokeResource(String username, String apiCode) {
        // todo 加锁，原子操作避免并发的错误
        InvokeInfoDO invokeInfo = invokeInfoRepository.getOne(username);
        if (ObjectUtil.isNotEmpty(invokeInfo)&&invokeInfo.getInvokeTimes()<invokeInfo.getTotalTimes()){
            // 事务，保证都能成功
            invokeInfo.setInvokeTimes(invokeInfo.getInvokeTimes()+1);
            invokeInfoRepository.updateById(invokeInfo);
            // 今日对应接口的调用次数+1
            String formattedDate = DateUtil.format(DateUtil.date(), "yyyy/MM/dd");
            RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(RedisCodeUtil.generateRankListKey(INVOKE_TIME_RANK_CODE, formattedDate));
            scoredSortedSet.addScore(apiCode,1);
            return true;
        }
        return false;
    }

    @Override
    public List<ApiResource> listApi(List<String> codes) {
        if (CollectionUtil.isEmpty(codes)){
            return new ArrayList<>();
        }
        List<ApiResourceDO> resourceDOS = apiResourceRepository.list(Wrappers.<ApiResourceDO>lambdaQuery().in(ApiResourceDO::getCode, codes));
        return apiResourceConvert.listDo2modal(resourceDOS);
    }
}
