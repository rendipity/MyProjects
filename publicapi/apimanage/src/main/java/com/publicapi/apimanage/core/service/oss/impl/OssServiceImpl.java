package com.publicapi.apimanage.core.service.oss.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.publicapi.apimanage.core.properties.OssProperties;
import com.publicapi.apimanage.core.service.oss.OssService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Service
public class OssServiceImpl implements OssService {
    @Resource
    OssProperties ossProperties;

    // 从环境变量中获取RAM用户的访问密钥（AccessKeyId和AccessKeySecret）
    public  String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
    public  String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");

    public  String putFile(String name, byte[] data) {
        OSSClient client = new OSSClient(ossProperties.endpoint, accessKeyId, accessKeySecret);
        String bucket = ossProperties.bucketName;
        InputStream is = new ByteArrayInputStream(data);
        PutObjectResult putObjectResult = client.putObject(bucket, name, is);
        client.shutdown();
        // 这里返回图片的访问路径
        return generateUrl(name);
    }
    private String generateUrl(String name){
        return ossProperties.endpoint.replace("https://","https://"+ossProperties.bucketName+".")+"/"+name;
    }

}
