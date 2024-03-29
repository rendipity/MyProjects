package com.publicapi.exception;

import cn.hutool.json.JSONUtil;
import com.publicapi.modal.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static com.publicapi.enums.ErrorResultEnum.ROUTE_NOT_EXIST;
import static com.publicapi.enums.ErrorResultEnum.SYSTEM_EXCEPTION;

@Slf4j
@Order(-1)
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ApiManageException) {
            ApiManageException dynamicRouteException = (ApiManageException)ex;
            ex.printStackTrace();
            return printResponse(exchange.getResponse(),Result.fail(dynamicRouteException.getErrorCode(),dynamicRouteException.getErrorMessage()));
        }else if (ex instanceof ResponseStatusException){
            ex.printStackTrace();
            return printResponse(exchange.getResponse(),Result.fail(ROUTE_NOT_EXIST));
        } else{
            Exception exception = (Exception)ex;
            ex.printStackTrace();
            return printResponse(exchange.getResponse(),Result.fail(SYSTEM_EXCEPTION));
        }
    }

    private Mono<Void> printResponse(ServerHttpResponse response,Result<Object> result){
        // 状态码
        //response.setStatusCode(HttpStatus.FORBIDDEN);
        // 响应头
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 响应体
        byte[] responseContent = JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(responseContent)));
    }
}
