package com.publicapi.sdk.client;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class LJPublicApiClient {

    private String accessKey;

    private String secretKey;

    public LJPublicApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String get(String url, Map<String, String> headers, Map<String,String> params){
        // 拼接 params参数
        url = joinParams(url,params);
        // 加入鉴权参数
        putAuthentication(headers,params);
        try {
            HttpResponse httpResponse = HttpRequest.get(url).addHeaders(headers).execute();
            return httpResponse.body();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public String get(String url, String paramsJson){
        return get(url, new HashMap<>(),JSONUtil.toBean(paramsJson, Map.class));
    }

    public String post(String url, Map<String,String> headers,Map<String,String> bodies){
        // 加入鉴权参数
        putAuthentication(headers,bodies);
        HttpResponse response = HttpRequest.post(url).body(JSONUtil.toJsonStr(bodies))
                .addHeaders(headers)
                .execute();
        return response.body();
    }

    public String post(String url, String bodiesJson){
        return post(url,new HashMap<>(), JSONUtil.toBean(bodiesJson, HashMap.class));
    }

    private String joinParams(String url, Map<String, String> params){
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url).append("?");
        for(Map.Entry<String, String> paramEntry : params.entrySet()){
            urlBuilder.append(paramEntry.getKey()).append("=").append(paramEntry.getValue()).append("&");
        }
        // 删除末尾的符号
        urlBuilder.deleteCharAt(urlBuilder.length()-1);
        return urlBuilder.toString();
    }
    private void putAuthentication(Map<String, String> headers,Map<String, String> params){
        String accessKey = this.accessKey;
        String nonce = IdUtil.fastUUID();
        String timestamp = Long.toString(System.currentTimeMillis());
        // accessKey
        headers.put("accessKey", accessKey);
        // nonce
        headers.put("nonce", nonce);
        // timestamp
        headers.put("timestamp", timestamp);
        // sign
        String sign = generateSign(accessKey,nonce,timestamp,params);
        headers.put("sign",sign);
    }
    String generateSign(String accessKey,String nonce,String timestamp,Map<String,String> params){
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1,this.secretKey.getBytes());
        String content = accessKey+"-"+nonce+"-"+timestamp+"-"+JSONUtil.toJsonStr(params);
        String sign = mac.digestHex(content);
        return sign;
    }

    public static void main(String[] args) throws InterruptedException {
        LJPublicApiClient client = new LJPublicApiClient("1f481f72b6c843e5a368e43dafdd666a","f0b9745c45b04c5cbcf189f01d805c98");
        /*CountDownLatch latch = new CountDownLatch(5);
        // post
        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                String url = "http://localhost:8080/XBohaU/serviceapi/student";
                String body ="{\"id\":\"20011117\",\"name\":\"lijie\",\"age\":20}";
                Object result = client.post(url, body);
                System.out.println(result);
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("并发执行完成");*/
        // get
        String url2 = "http://localhost:8080/serviceapi/hello";
        String params = "{\"name\":\"张三\"}";
        String result2 = client.get(url2, params);
        System.out.println(result2);
    }
}
