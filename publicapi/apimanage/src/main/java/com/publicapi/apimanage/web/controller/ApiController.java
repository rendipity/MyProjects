package com.publicapi.apimanage.web.controller;

import com.aliyun.teautil.Common;
import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.biz.constants.RoleEnum;
import com.publicapi.apimanage.biz.convert.ApiResourceConvert;
import com.publicapi.apimanage.biz.service.ApiService;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.common.qto.ApiEvent;
import com.publicapi.apimanage.common.qto.ApiPageQuery;
import com.publicapi.apimanage.web.exception.AccessControl;
import com.publicapi.apimanage.web.vo.api.AddApiVO;
import com.publicapi.apimanage.web.vo.api.DetailsApiVO;
import com.publicapi.apimanage.web.vo.api.ListApiVO;
import com.publicapi.apimanage.web.vo.api.UpdateApiVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    ApiService apiService;

    @Resource
    ApiResourceConvert apiResourceConvert;

    /**
     * 新增api
     */
    @PostMapping("/create")
    @AccessControl(RoleEnum.DEVELOPER)
    public Result<String> createApi(@RequestBody AddApiVO addApiVO){
            ApiResource apiResource = apiResourceConvert.addVo2modal(addApiVO);
            return Result.success(apiService.createApi(apiResource));
    }

    /**
     * 编辑api
     */
    @PostMapping("/update")
    @AccessControl(RoleEnum.DEVELOPER)
    public Result<Boolean> updateApi(@RequestBody UpdateApiVO updateApiVO){
        return Result.success(apiService.updateApi(apiResourceConvert.updateVo2modal(updateApiVO)));
    }

    /**
     * 根据group 查询api列表
     */
    @PostMapping("/list")
    public Result<CommonPage<ListApiVO>> pageApi(@RequestBody ApiPageQuery apiPageQuery){
        CommonPage<ApiResource> apiCommonPage = apiService.pageApi(apiPageQuery);
        CommonPage<ListApiVO> resultData= CommonPage.convert(apiCommonPage,apiResourceConvert.listModal2Vo(apiCommonPage.getLists()));
        return Result.success(resultData);
    }

    /**
     * api详情
     */
    @GetMapping("/details")
    public Result<DetailsApiVO> detailsApi(String apiCode){
         return Result.success(apiResourceConvert.modal2DetailsVO(apiService.detailsApi(apiCode)));
    }

    /**
     * 禁用、启用Api
     */
    @PostMapping("/event")
    @AccessControl(RoleEnum.DEVELOPER)
    public Result<Boolean> eventApi(@RequestBody ApiEvent event){
        // todo 开发者只能禁用自己上传的接口  管理员可以禁用所有接口
        return Result.success(apiService.eventApi(event));
    }

    /**
     * 删除api
     */
    @GetMapping("/remove")
    @AccessControl(RoleEnum.DEVELOPER)
    public Result<Boolean> removeApi(String apiCode){
        // todo 开发者只能删除自己上传的接口  管理员可以删除所有接口
        return Result.success(apiService.removeApi(apiCode));
    }

}
