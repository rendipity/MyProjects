package com.publicapi.apimanage.biz.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    public String uploadFile(MultipartFile mf);
}
