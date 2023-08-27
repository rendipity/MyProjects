package com.publicapi.filter.globalfilter;

import com.publicapi.dubboclient.ApiClient;
import com.publicapi.exception.ApiManageException;
import com.publicapi.util.RequestUtil;
import com.publicapi.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import static com.publicapi.constant.FilterOrder.INVOKE_TIME_GLOBAL_FILTER;
import static com.publicapi.enums.ErrorResultEnum.INVOKE_TIME_NOT_ENOUGH;

@Component
@Slf4j
public class InvokeTimeGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private ApiClient apiClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=================InvokeTimeGlobalFilter================");
        // 获取username和apiCode
        ServerHttpRequest request = exchange.getRequest();
        String username = request.getHeaders().getFirst("username");
        String apiCode = RequestUtil.getApiCode(request.getURI());
        // 调用接口扣减次数，如果扣减失败则禁止调用
        Boolean deductionResult = ResultUtil.isSuccess(apiClient.invokeResource(username, apiCode));
        if (!deductionResult){
            throw new ApiManageException(INVOKE_TIME_NOT_ENOUGH);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return INVOKE_TIME_GLOBAL_FILTER;
    }


}
