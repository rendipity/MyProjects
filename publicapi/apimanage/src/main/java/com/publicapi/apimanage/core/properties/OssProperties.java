package com.publicapi.apimanage.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.oss")
@Component
@Data
public class OssProperties {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。 https://oss-cn-hangzhou.aliyuncs.com
   public  String endpoint;
    // Bucket名称。
   public  String bucketName ;

}
