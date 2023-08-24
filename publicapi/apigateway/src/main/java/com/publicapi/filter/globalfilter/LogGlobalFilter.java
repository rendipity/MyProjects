package com.publicapi.filter.globalfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.publicapi.constant.FilterOrder.LOG_GLOBAL_FILTER;


@Component
@Slf4j
public class LogGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("请求进入");
        printRequestInfo(exchange);
        return chain.filter(exchange).then(Mono.fromRunnable(() ->
        {
            printResponseInfo(exchange);
        }));
    }

    @Override
    public int getOrder() {
        return LOG_GLOBAL_FILTER;
    }
    void printRequestInfo(ServerWebExchange exchange){
        log.info("请求请求信息:");
    }
    void printResponseInfo(ServerWebExchange exchange){
        log.info("请求响应信息:");
    }
}
