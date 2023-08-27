package com.publicapi.filter.globalfilter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.publicapi.dubboclient.AuthenticationClient;
import com.publicapi.exception.ApiManageException;
import com.publicapi.modal.GatewayContext;
import com.publicapi.modal.authentication.UserAuthDTO;
import com.publicapi.util.AuthUtil;
import com.publicapi.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.publicapi.constant.AccessConstant.*;
import static com.publicapi.constant.FilterOrder.ACCESS_VERIFICATION_GLOBAL_FILTER;
import static com.publicapi.enums.ErrorResultEnum.*;


@Component
@Slf4j
public class AccessVerificationGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private AuthenticationClient authenticationClient;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=================AccessVerificationGlobalFilter================");
        ServerHttpRequest request = exchange.getRequest();
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
        // 参数校验
        checkParameterValidate(accessKeys, sign, nonce, timestamp);
        // 判断timestamp是否过期
        checkExpired(Long.parseLong(timestamp));
        // 判断nonce是否重复 查询redis
        checkRepeatRequest(nonce);
        // userInfo
        UserAuthDTO userAuthDTO = ResultUtil.isSuccess(authenticationClient.getAuthByAppKey(accessKeys));
        if (ObjectUtil.isEmpty(userAuthDTO)){
            throw new ApiManageException(APP_KEY_NOT_EXIST);
        }
        // params
        // GET 方法从url的params参数里取
        HashMap<String,String> params = new HashMap<>();
        if (request.getMethod() == HttpMethod.GET){
            getParams(request.getURI().getQuery(),params);
        }
        // POST 方法从请求体里面取
        else{
            MediaType contentType = headers.getContentType();
            long contentLength = headers.getContentLength();
            if (contentLength >0 && MediaType.APPLICATION_JSON.equals(contentType)){
                GatewayContext context = (GatewayContext)exchange.getAttributes().get(GatewayContext.CACHE_GATEWAY_CONTEXT);
                String body = context.getCacheBody();
                params = JSONUtil.toBean(body, HashMap.class);
            }
        }
        // 校验sign
        boolean checkedSignature = AuthUtil.checkSignature(sign,
                accessKeys,
                userAuthDTO.getSecretKey(),
                JSONUtil.toJsonStr(params),
                nonce,
                timestamp);
        // 校验失败 拦截返回
        if (!checkedSignature){
            // todo 先把sign去掉
            log.info("sign不合法");
           // throw new ApiManageException(SIGN_INVALID);
        }
        // 校验成功将 userid和username存在请求头中，后续记录次数需要使用
        ServerHttpRequest newRequest = request.mutate()
                .header("userId",Integer.toString(userAuthDTO.getUserId()))
                .header("username",userAuthDTO.getUsername())
                .build();
       return chain.filter(exchange.mutate().request(newRequest).build()).then(Mono.fromRunnable(() -> {
        log.info("AccessVerificationGlobalFilter后置逻辑");
        }
        ));
    }

    @Override
    public int getOrder() {
        return ACCESS_VERIFICATION_GLOBAL_FILTER;
    }
    // name=zhangsan&param1=value1&param2=value2
    private void getParams(String query, HashMap<String,String> paramsMap){
        List<String> paramsPair = StrUtil.split(query, "&");
        for(String param: paramsPair){
            int i = param.indexOf("=");
            String key = param.substring(0,i);
            String value = param.substring(i+1);
            paramsMap.put(key,value);
        }
    }

    private void checkParameterValidate(String accessKeys, String sign, String nonce, String timestamp){
        if (ObjectUtil.isEmpty(accessKeys)
                ||ObjectUtil.isEmpty(sign)
                ||ObjectUtil.isEmpty(nonce)
                ||ObjectUtil.isEmpty(timestamp)){
            log.info("参数不合法! accessKeys:{}, sign:{}, nonce:{}, timestamp:{}",accessKeys, sign, nonce, timestamp);
            throw new ApiManageException(PARAMETER_EXCEPTION);
        }
    }
    private void checkExpired(long timestamp){
        long currentTime = System.currentTimeMillis();
        long gap = 0;
        if ((gap=Math.abs(currentTime-timestamp))> (long)NONCE_INTERVAL*60*1000) {
            log.info("请求已过期! currentTime:{}, timestamp:{}, gap:{}",currentTime,timestamp,gap);
            throw new ApiManageException(TIMESTAMP_INVALID);
        }
    }

    /**
     * 判断本次的nonce值是否重复，如果不重复则将nonce保存下来
     * @param nonce
     */
    private void checkRepeatRequest(String nonce){
        RBucket<Object> bucket = redissonClient.getBucket(redisKey(nonce));
        if (ObjectUtil.isNotEmpty(bucket.get())){
            log.info("重复请求! nonce:{}",nonce);
            throw new ApiManageException(REQUEST_REPEAT);
        }
        bucket.set(nonce,NONCE_INTERVAL, TimeUnit.MINUTES);
    }

    private String redisKey(String nonce){
        return BIZ+":"+SERVICE+":"+FIELD+":"+nonce;
    };
}
