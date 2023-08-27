package com.publicapi.filter.gatewayfilter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.redisson.api.RateType.OVERALL;

@Component
// 命名必须为   $name+GatewayFilterFactory 的格式 name会作为配置时填入的filter的name
@Slf4j
public class  RateLimiterGatewayFilterFactory
        // 在 AbstractGatewayFilterFactory 中指定我们的配置类，会在创建gatewayFilter将参数通过配置类传入
        extends AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> {

    @Resource
    private RedissonClient redissonClient;
    // 指定配置类
    public RateLimiterGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(config.getRouteId());
        // todo rate = frequency
        rateLimiter.setRate(OVERALL, config.frequency, 1, RateIntervalUnit.SECONDS);
        return new RateLimiterGatewayFilter(rateLimiter);
    }

    // 指定config类中参数的顺序
    @Override
    public List<String> shortcutFieldOrder() {
        return Stream.of("frequency").collect(Collectors.toList());
    }

    @Data
    public static class Config implements HasRouteId {
        // 频率 每秒调用次数
        private int frequency;

        private String routeId;

        @Override
        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        @Override
        public String getRouteId() {
            return routeId;
        }
    }

}
