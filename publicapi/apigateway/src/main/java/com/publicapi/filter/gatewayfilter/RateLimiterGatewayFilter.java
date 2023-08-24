package com.publicapi.filter.gatewayfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.publicapi.constant.FilterOrder.GATEWAY_FILTER_TEST1;

@Slf4j
public class RateLimiterGatewayFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("GatewayFilter1前置逻辑");
        String url = exchange.getRequest().getPath().pathWithinApplication().value();
        log.info("请求URL:" + url);
        log.info("method:" + exchange.getRequest().getMethod());
        return chain.filter(exchange).then(Mono.fromRunnable(() ->
            {
                log.info("GatewayFilter1后置逻辑");
            })
        );
    }

    @Override
    public int getOrder() {
        return GATEWAY_FILTER_TEST1;
    }
}
