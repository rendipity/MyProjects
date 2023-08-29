package com.publicapi.apimanage.biz.constants;

public class ApiManageConstants {

    public static final String  BIZ_NAME = "apiManage";

    public static final String  SERVICE_NAME = "user";

    public static final String  REGISTER_AUTH_CODE_INTERVAL = "registerAuthCodeInterval";

    public static final String  SENSITIVE_AUTH_CODE_INTERVAL = "sensitiveAuthCodeInterval";

    public static final String  REGISTER_AUTH_CODE = "registerAuthCode";

    public static final String  SENSITIVE_AUTH_CODE = "sensitiveAuthCode";
    public static final String  IP = "ip";
    public static final String  PHONE = "phone";

    public static final String  USELESS_VALUE = "1";

    // 发送验证码间隔 单位: 分钟
    public static final Integer  SEND_AUTH_CODE_INTERVAL = 1;

    //验证码有效时间 单位: 分钟
    public static final Integer  AUTH_CODE_TTL = 5;

    // 默认用户名
    public static final String  NICK_NAME = "....";

    // 头像
    public static final String  HEAD_PHOTO = "https://bloglijie.oss-cn-beijing.aliyuncs.com/img/v2-698a5ea814e785a2a7f1c00237c38b6e_r.jpeg";

    public static final String  PHONE_REGULAR= "/^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$/";

    public static final String IP_REGULAR = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3})$";

    public static final String INVOKE_TIME_RANK_CODE = "invokeTime";
}
