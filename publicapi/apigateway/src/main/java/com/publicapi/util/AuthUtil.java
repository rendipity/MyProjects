package com.publicapi.util;

import cn.hutool.crypto.SecureUtil;

import java.util.HashMap;

public class AuthUtil {

    /**
     * 生成签名
     */
    public static String generateSignature(String accessKey, String secretKey, String params, String nonce, String timeStamp){
        HashMap<String, String> signMap = new HashMap<>();
        signMap.put("accessKey",accessKey);
        signMap.put("params",params);
        signMap.put("nonce",nonce);
        signMap.put("timeStamp",timeStamp);
        return new String(SecureUtil.hmacSha1(secretKey).digest(signMap.toString()));
    }

    /**
     * 校验签名
     */
    public static boolean checkSignature(String requestSign, String accessKey, String secretKey, String params, String nonce, String timeStamp){
        String sign = generateSignature(accessKey, secretKey, params, nonce, timeStamp);
        return sign.equals(requestSign);
    }
}
