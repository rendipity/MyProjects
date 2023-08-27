package com.publicapi.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

import java.util.HashMap;

public class AuthUtil {

    /**
     * 生成签名
     */
    public static String generateSignature(String accessKey, String secretKey, String params, String nonce, String timestamp){
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1,secretKey.getBytes());
        String content = accessKey+"-"+nonce+"-"+timestamp+"-"+params;
        return mac.digestHex(content);
    }

    /**
     * 校验签名
     */
    public static boolean checkSignature(String requestSign, String accessKey, String secretKey, String params, String nonce, String timeStamp){
        String sign = generateSignature(accessKey, secretKey, params, nonce, timeStamp);
        return sign.equals(requestSign);
    }
}
