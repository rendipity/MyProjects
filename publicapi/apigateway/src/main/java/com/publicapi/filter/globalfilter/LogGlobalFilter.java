package com.publicapi.filter.globalfilter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.publicapi.modal.GatewayContext;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static com.publicapi.constant.FilterOrder.LOG_GLOBAL_FILTER;


@Component
@Slf4j
public class LogGlobalFilter implements GlobalFilter, Ordered {

    /**
     * default HttpMessageReader
     */
    private static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 打印请求信息
        return printRequestInfo(exchange,chain);
    }

    @Override
    public int getOrder() {
        return LOG_GLOBAL_FILTER;
    }

    Mono<Void> printRequestInfo(ServerWebExchange exchange, GatewayFilterChain chain){
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
        } else {
            MediaType contentType = headers.getContentType();
            long contentLength = headers.getContentLength();
            GatewayContext gatewayContext = new GatewayContext();
            exchange.getAttributes().put(GatewayContext.CACHE_GATEWAY_CONTEXT,gatewayContext);
            if(contentLength>0){
                if(MediaType.APPLICATION_JSON.equals(contentType) || MediaType.APPLICATION_JSON_UTF8.equals(contentType)){
                    return readBody(exchange, chain,gatewayContext,builder);
                } else if ( MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)) {
                    return readFormData(exchange, chain,gatewayContext,builder);
                }
            }
        }
        builder.append("===============================================\n");
        log.info(builder.toString());
        return chain.filter(exchange).then(Mono.fromRunnable(() ->{
            printResponseInfo(exchange);
        }));
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

    /**
     * ReadFormData
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> readFormData(ServerWebExchange exchange, GatewayFilterChain chain, GatewayContext gatewayContext, StringBuilder builder){
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return exchange.getFormData()
                .doOnNext(multiValueMap -> {
                    gatewayContext.setFormData(multiValueMap);
                    builder.append(multiValueMap).append("\n");
                    log.info(builder.toString());
                })
                .then(Mono.defer(() -> {
                    Charset charset = headers.getContentType().getCharset();
                    charset = charset == null? StandardCharsets.UTF_8:charset;
                    String charsetName = charset.name();
                    MultiValueMap<String, String> formData = gatewayContext.getFormData();
                    /**
                     * formData is empty just return
                     */
                    if(null == formData || formData.isEmpty()){
                        return chain.filter(exchange);
                    }
                    StringBuilder formDataBodyBuilder = new StringBuilder();
                    String entryKey;
                    List<String> entryValue;
                    try {
                        /**
                         * repackage form data
                         */
                        for (Map.Entry<String, List<String>> entry : formData.entrySet()) {
                            entryKey = entry.getKey();
                            entryValue = entry.getValue();
                            if (entryValue.size() > 1) {
                                for(String value : entryValue){
                                    formDataBodyBuilder.append(entryKey).append("=").append(URLEncoder.encode(value, charsetName)).append("&");
                                }
                            } else {
                                formDataBodyBuilder.append(entryKey).append("=").append(URLEncoder.encode(entryValue.get(0), charsetName)).append("&");
                            }
                        }
                    }catch (UnsupportedEncodingException e){
                        //ignore URLEncode Exception
                    }
                    /**
                     * substring with the last char '&'
                     */
                    String formDataBodyString = "";
                    if(formDataBodyBuilder.length()>0){
                        formDataBodyString = formDataBodyBuilder.substring(0, formDataBodyBuilder.length() - 1);
                    }
                    /**
                     * get data bytes
                     */
                    byte[] bodyBytes =  formDataBodyString.getBytes(charset);
                    int contentLength = bodyBytes.length;
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                            exchange.getRequest()) {
                        /**
                         * change content-length
                         * @return
                         */
                        @Override
                        public HttpHeaders getHeaders() {
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.putAll(super.getHeaders());
                            if (contentLength > 0) {
                                httpHeaders.setContentLength(contentLength);
                            } else {
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                            }
                            return httpHeaders;
                        }

                        /**
                         * read bytes to Flux<Databuffer>
                         * @return
                         */
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return DataBufferUtils.read(new ByteArrayResource(bodyBytes),new NettyDataBufferFactory(ByteBufAllocator.DEFAULT),contentLength);
                        }
                    };
                    ServerWebExchange mutateExchange = exchange.mutate().request(decorator).build();
                    return chain.filter(mutateExchange)
                            .then(Mono.fromRunnable(() -> printResponseInfo(exchange)));
                }));
    }

    /**
     * ReadJsonBody
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> readBody(ServerWebExchange exchange,GatewayFilterChain chain,GatewayContext gatewayContext, StringBuilder builder){
        /**
         * join the body
         */
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    /*
                     * read the body Flux<DataBuffer>, and release the buffer
                     */
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                        DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
                        DataBuffer  buffer = bufferFactory.wrap(bytes);
                        return Mono.just(buffer);
                    });
                    /**
                     * repackage ServerHttpRequest
                     */
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };
                    /**
                     * mutate exchage with new ServerHttpRequest
                     */
                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                    /**
                     * read body string with default messageReaders
                     */
                    return ServerRequest.create(mutatedExchange, messageReaders)
                            .bodyToMono(String.class)
                            .doOnNext(cacheBody->{
                                builder.append(cacheBody).append('\n');
                                log.info(builder.toString());
                                gatewayContext.setCacheBody(cacheBody);
                            }).then(chain.filter(mutatedExchange))
                            .then(Mono.fromRunnable(() -> printResponseInfo(exchange)));
                });
    }
}
