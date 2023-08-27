package com.publicapi.client;

import com.publicapi.sdk.client.LJPublicApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    LJPublicApiClient client;

    @GetMapping("/test")
    public String testGet(){
        String url = "http://localhost:8080/serviceapi/hello";
        String params = "{\"name\":\"张三\"}";
        return client.get(url,params);
    }

    @PostMapping("/test")
    public String testPost(){
        String url = "http://localhost:8080/XBohaU/serviceapi/student";
        String body ="{\"id\":\"20011117\",\"name\":\"lijie\",\"age\":20}";
        return  client.post(url, body);
    }
}
