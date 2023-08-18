package com.publicapi.apimanage.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.publicapi.apimanage.common.UserInfo;

public class TokenUtil {
    public static String SECRET = "lj_PublicAPI";

    public static String generate(UserInfo userInfo){
        return JWT.create()
                .setPayload("username",userInfo.getUsername())
                .setPayload("role",userInfo.getRole())
                .setPayload("phone",userInfo.getPhone())
                .setExpiresAt(DateUtil.date().offset(DateField.HOUR,24*3)) // 3天过期
                .setKey(SECRET.getBytes())
                .sign();
    }

    public static boolean verify(String token){
        // 验证token是否有效
        boolean result = JWTUtil.verify(token,SECRET.getBytes());
        try {
            // 验证token是否在有效期内
            JWTValidator.of(token).validateDate(DateUtil.date());
        }catch (ValidateException exception){
            result = false;
        }
        return result;
    }

    public static UserInfo parse(String token){
        JWT jwt = JWTUtil.parseToken(token);
        UserInfo user = new UserInfo();
        user.setUsername((String)jwt.getPayload("username"));
        user.setRole((String)jwt.getPayload("role"));
        user.setPhone((String)jwt.getPayload("phone"));
        return user;
    }

}
