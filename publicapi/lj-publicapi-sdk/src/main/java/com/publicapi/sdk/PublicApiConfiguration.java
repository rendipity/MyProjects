package com.publicapi.sdk;

import com.publicapi.sdk.client.LJPublicApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("lj.publicapi")
@ComponentScan
public class PublicApiConfiguration {
    private String accessKey;
    private String secretKey;
    @Bean
    public LJPublicApiClient createClient(){
        return new LJPublicApiClient(accessKey,secretKey);
    }
}
