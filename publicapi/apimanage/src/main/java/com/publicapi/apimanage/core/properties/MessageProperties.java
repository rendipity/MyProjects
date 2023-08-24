package com.publicapi.apimanage.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.message")
@Component
@Data
public class MessageProperties {

    public String endpoint;
}
