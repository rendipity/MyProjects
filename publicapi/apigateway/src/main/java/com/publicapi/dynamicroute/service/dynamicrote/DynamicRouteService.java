package com.publicapi.dynamicroute.service.dynamicrote;


import cn.hutool.core.util.IdUtil;
import com.publicapi.dynamicroute.apimodal.ApiResource;
import com.publicapi.dynamicroute.dubboclient.ApiClient;
import com.publicapi.dynamicroute.service.dynamicrote.convert.ApiResourceConvert;
import com.publicapi.modal.api.ApiResourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DynamicRouteService{

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource
    private ApiClient apiClient;

    @Resource
    private ApiResourceConvert apiResourceConvert;

    public void init(){
        log.info("init 执行了");
        List<ApiResourceDTO> apiResourceDTOS = apiClient.listApiResource();
        apiResourceDTOS.forEach(apiDto->{
            addRoute(apiResourceConvert.dto2modal(apiDto));
        });
    }

    public void  addRoute(ApiResource apiResource){
        RouteDefinition routeDefinition = new RouteDefinition();
        // route id
        routeDefinition.setId(IdUtil.simpleUUID());
        // uri
        //URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8001").build().toUri();
        String url = apiResource.getProtocol()+"://"+apiResource.getHost();
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
        routeDefinition.setUri(uri);
        // predicate
        PredicateDefinition pathPredicate = new PredicateDefinition();
        pathPredicate.setName("Path");
        Map<String,String> pathPredicateParams = new HashMap<>();
        String path = "/"+apiResource.getCode()+apiResource.getPath();
        //String path = "/testApi/serviceapi/hello";
        // 参数名并没有任何意义
        pathPredicateParams.put("_genkey_0",path);
        pathPredicate.setArgs(pathPredicateParams);
        PredicateDefinition methodPredicate = new PredicateDefinition();
        methodPredicate.setName("Method");
        Map<String,String> methodPredicateParams = new HashMap<>();
        //String httpMethod = "GET";
        String httpMethod = apiResource.getHttpMethod();
        methodPredicateParams.put("_genkey_0", httpMethod.toUpperCase());
        methodPredicate.setArgs(methodPredicateParams);
        routeDefinition.setPredicates(Stream.of(pathPredicate,methodPredicate).collect(Collectors.toList()));
        //routeDefinition.setPredicates(Stream.of(pathPredicate).collect(Collectors.toList()));
        // filter
        FilterDefinition filter = new FilterDefinition();
        filter.setName("StripPrefix");
        Map<String,String> filterParam = new HashMap<>();
        filterParam.put("_genkey_0","1");
        filter.setArgs(filterParam);
        routeDefinition.setFilters(Stream.of(filter).collect(Collectors.toList()));
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        // 刷新网关路由配置 生效---
        //log.info("刷新网关");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("addRouteSuccess");
    }
}
