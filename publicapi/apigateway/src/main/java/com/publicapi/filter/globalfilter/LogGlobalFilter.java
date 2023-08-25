package com.publicapi.filter.globalfilter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.publicapi.constant.FilterOrder.LOG_GLOBAL_FILTER;


@Component
@Slf4j
public class LogGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 打印请求信息
        printRequestInfo(exchange);
        // 重写 ServerHttpResponse 提供一个直接获取body的方法
        return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                printResponseInfo(exchange);
        }));
    }

    @Override
    public int getOrder() {
        return LOG_GLOBAL_FILTER;
    }

    void printRequestInfo(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
        StringBuilder builder = new StringBuilder();
        builder.append("\n=================== Gateway Request ===================\n");
        // 时间
        String time = DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss");
        builder.append(time).append("\n");
        // 请求行
        String methodValue = request.getMethodValue();
        URI requestURI = request.getURI();
        String path = requestURI.getPath();
        builder.append(methodValue).append(" ").append(path).append("\n\n");
        // 请求头
        builder.append("headers\n");
        // 获取请求头信息
        HttpHeaders headers = request.getHeaders();
        // accessKey
        String accessKeys = headers.getFirst("accessKey");
        // sign
        String sign = headers.getFirst("sign");
        // nonce
        String nonce = headers.getFirst("nonce");
        // timestamp
        String timestamp = headers.getFirst("timestamp");
        builder.append("accessKeys = ").append(accessKeys).append("\n");
        builder.append("sign = ").append(sign).append("\n");
        builder.append("nonce = ").append(nonce).append("\n");
        builder.append("timestamp = ").append(timestamp).append("\n");
        builder.append("\n");
        // 请求体 GET
        if (request.getMethod() == HttpMethod.GET) {
            builder.append("params:\n");
            String query = requestURI.getQuery();
            List<String> paramsPair = StrUtil.split(query, "&");
            for(String param: paramsPair){
                int i = param.indexOf("=");
                String key = param.substring(0,i);
                String value = param.substring(i+1);
                builder.append(key).append("=").append(value).append("\n");
            }
        }
        builder.append("===============================================\n");
        log.info(builder.toString());
    }
    void printResponseInfo(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        StringBuilder builder = new StringBuilder();
        builder.append("\n=================== Gateway Response ===================\n");
        // 时间
        String time = DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss");
        builder.append(time).append("\n");
        // 响应状态码
        int httpStatus = response.getStatusCode().value();
        builder.append("status:").append(httpStatus).append("\n");
        builder.append("===============================================\n");
        log.info(builder.toString());
    }
}
