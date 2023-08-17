package com.publicapi.apimanage.common;


public class UserContext {
    public static ThreadLocal<UserInfo> userThreadLocal = new ThreadLocal<>();

    public static UserInfo get() {
        return userThreadLocal.get();
    }

    public static void set(UserInfo userinfo) {
        userThreadLocal.set(userinfo);
    }
    public static void remove() {
        userThreadLocal.remove();
    }
}
