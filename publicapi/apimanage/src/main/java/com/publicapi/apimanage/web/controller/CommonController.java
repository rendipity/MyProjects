package com.publicapi.apimanage.web.controller;


import com.publicapi.apimanage.biz.service.CommonService;
import com.publicapi.apimanage.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    CommonService commonService;
    // 上传图片

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file){
        return Result.success(commonService.uploadFile(file));
    }

    // 获取字典
}
