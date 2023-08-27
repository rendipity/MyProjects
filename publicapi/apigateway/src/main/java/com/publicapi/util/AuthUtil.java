package com.publicapi.util;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

public class AuthUtil {

    /**
     * 生成签名
     */
    public static String generateSignature(String accessKey, String secretKey, String params, String nonce, String timestamp){
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1,secretKey.getBytes());
        String content = accessKey+"-"+nonce+"-"+timestamp+"-"+params;
        String sign = mac.digestHex(content);
        return sign;
    }

    /**
     * 校验签名
     */
    public static boolean checkSignature(String requestSign, String accessKey, String secretKey, String params, String nonce, String timeStamp){
        String sign = generateSignature(accessKey, secretKey, params, nonce, timeStamp);
        return sign.equals(requestSign);
    }
}
