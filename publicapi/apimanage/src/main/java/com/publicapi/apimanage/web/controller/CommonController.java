package com.publicapi.apimanage.web.controller;

import com.aliyun.oss.OSS;
import com.publicapi.apimanage.common.Result;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CommonController {


    @Resource
    OSS oss;
    // 上传图片
    public Result<String> upload(){
        return Result.success("success");
    }

    // 获取字典
}
