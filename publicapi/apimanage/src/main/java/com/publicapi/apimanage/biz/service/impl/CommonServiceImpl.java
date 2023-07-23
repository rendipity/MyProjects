package com.publicapi.apimanage.biz.service.impl;

import cn.hutool.core.util.IdUtil;
import com.publicapi.apimanage.biz.service.CommonService;
import com.publicapi.apimanage.core.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    OssService ossService;

    @Override
    public String uploadFile(MultipartFile mf) {
        String ofn = mf.getOriginalFilename();
        String ext = getFileExtName(ofn);
        String dayStr = getNowString("yyyyMMdd");
        // 文件名 = 时间+id+ext
        String filename = String.format("image/%s/%s%s", dayStr, IdUtil.simpleUUID(), ext);
        try {
            String fileUrl = ossService.putFile(filename, mf.getBytes());
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFileExtName(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    public static String getNowString(String ff) {
        SimpleDateFormat format = new SimpleDateFormat(ff);
        Date now = new Date();
        String created = format.format(now);
        return created;
    }
}
