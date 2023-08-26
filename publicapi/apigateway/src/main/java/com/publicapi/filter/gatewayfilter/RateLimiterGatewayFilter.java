package com.publicapi.filter.gatewayfilter;

import com.publicapi.exception.ApiManageException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.publicapi.constant.FilterOrder.RATE_LIMITER_GATEWAY_FILTER;
import static com.publicapi.enums.ErrorResultEnum.OVER_THE_RATE_LIMIT;

@Slf4j
public class RateLimiterGatewayFilter implements Ordered, GatewayFilter {

    private RRateLimiter rateLimiter;

    public RateLimiterGatewayFilter(RRateLimiter rateLimiter) {
      this.rateLimiter = rateLimiter;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("===============RateLimiterGatewayFilter=================");
        // 获取当前请求的apiCode
        //String apiCode = RequestUtil.getApiCode(exchange.getRequest().getURI());
        if (!rateLimiter.tryAcquire(1)) {
            throw new ApiManageException(OVER_THE_RATE_LIMIT);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return RATE_LIMITER_GATEWAY_FILTER;
    }
    private String getRateLimiterRedisCode(String code){
        return "aaa";
    }
}
