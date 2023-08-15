package com.publicapi.apimanage.biz.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.biz.convert.ApiUserConvert;
import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.common.exception.ApiManageException;
import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.service.textmessage.TextMessageService;
import com.publicapi.apimanage.core.service.textmessage.template.AuthCodeTemplateParam;
import com.publicapi.apimanage.core.service.user.UserDomainService;
import com.publicapi.apimanage.web.vo.user.LoginUserVO;
import com.publicapi.apimanage.web.vo.user.RegisterUserVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


import static cn.hutool.core.date.DateField.MINUTE;
import static com.publicapi.apimanage.biz.constants.ApiManageConstants.*;
import static com.publicapi.apimanage.common.enums.ErrorResultEnum.*;
import static com.publicapi.constants.APIConstants.ENABLE;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TextMessageService messageService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ApiUserConvert userConvert;


    @Override
    public Boolean sendRegisterAuthCode(String phone, String ip) {
        // todo 加锁，使得校验和限制手机号变成一个原子操作
        // 校验该手机号和ip是否被限制
        checkLimited(REGISTER_AUTH_CODE_INTERVAL,phone, ip);
        // 生成验证码并保存到redis
        String code = generateAuthCode();
        cacheAuthCode(REGISTER_AUTH_CODE,phone,code);
        //发送消息
        try {
            messageService.sendTextMsg(phone, MessageEnum.REGISTER_AUTH_CODE,new AuthCodeTemplateParam(code));
            // 限制手机号和ip
            limitSend(REGISTER_AUTH_CODE_INTERVAL,phone,ip);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("发送短信验证码失败 phone={},code={}",phone,code);
            throw new ApiManageException(SEND_AUTH_CODE_ERROR);
        }
    }

    @Override
    public Boolean sendSensitiveAuthCode(String phone, String ip) {
        // todo 加锁，使得校验和限制手机号变成一个原子操作
        // 校验该手机号和ip是否被限制
        checkLimited(SENSITIVE_AUTH_CODE_INTERVAL, phone, ip);
        // 生成验证码并保存到redis
        String code = generateAuthCode();
        cacheAuthCode(SENSITIVE_AUTH_CODE,phone,code);
        //发送消息
        try {
            messageService.sendTextMsg(phone, MessageEnum.SENSITIVE_AUTH_CODE,new AuthCodeTemplateParam(code));
            // 限制手机号和ip
            limitSend(SENSITIVE_AUTH_CODE_INTERVAL, phone,ip);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("发送短信验证码失败 phone={},code={}",phone,code);
            throw new ApiManageException(SEND_AUTH_CODE_ERROR);
        }
    }

    @Override
    public Boolean register(RegisterUserVO registerUserVO) {
        // todo 加锁，使得校验验证码和校验成功删除验证码变成一个原子操作
        // 判断验证码是否正确
        checkAuthCode(REGISTER_AUTH_CODE,registerUserVO.getPhone(),registerUserVO.getAuthCode());
        // 判断手机号是否重复
        checkPhone(registerUserVO.getPhone());
        // 判断用户名是否重复
        checkUsername(registerUserVO.getUsername());
        ApiUser user = userConvert.vo2Modal(registerUserVO);
        // 生成 appKey 和 secretKey
        generateAuthentication(user);
        user.setRole(USER);
        user.setNickName(NICK_NAME);
        user.setHeadPhoto(HEAD_PHOTO);
        user.setStatus(ENABLE);
        userDomainService.createUser(user);
        removeCacheAuthCode(REGISTER_AUTH_CODE,registerUserVO.getPhone());
        return true;
    }

    @Override
    public Boolean login(LoginUserVO loginUserVO) {
        return null;
    }

    private void checkAuthCode(String usage,String phone, String authCode){
        List<String> cacheAuthCode = getCacheAuthCode(usage, phone);
        for(String code : cacheAuthCode){
            if (code.equals(authCode)) {
                return;
            }
        }
        throw new ApiManageException(AUTH_CODE_ERROR);
    }

    private void checkPhone(String phone){
        ApiUser user = userDomainService.getUserByPhone(phone);
        if (ObjectUtil.isNotNull(user)){
            throw new ApiManageException(PHONE_HAS_EXISTED);
        }
    }
    private void  checkUsername(String username){
        ApiUser user = userDomainService.getUserByUsername(username);
        if (ObjectUtil.isNotNull(user)){
            throw new ApiManageException(USERNAME_HAS_EXISTED);
        }
    }

    private void generateAuthentication(ApiUser user){
        String appKey = IdUtil.fastSimpleUUID();
        String appSecret = IdUtil.fastSimpleUUID();
        user.setAppKey(appKey);
        user.setAppSecret(appSecret);
    }

    /**
     * 校验该ip和手机号是否处于被限制的状态
     * @param usage
     * @param phone
     * @param ip
     */
    private void checkLimited(String usage, String phone, String ip){
        String phoneCode = generateRedisCode(usage,PHONE,phone);
        String ipCode = generateRedisCode(usage,IP,ip);
        // 批量执行
        RBatch batch = redissonClient.createBatch();
        batch.getBucket(phoneCode).getAsync();
        batch.getBucket(ipCode).getAsync();
        BatchResult<?> batchResult = batch.execute();
        for (Object response : batchResult.getResponses()) {
            if (ObjectUtil.isNotNull(response)){
                throw new ApiManageException(SEND_AUTH_CODE_IS_LIMITED);
            }
        }

    }

    /**
     * 生成验证码
     * @return
     */
    private String generateAuthCode(){
        return RandomUtil.randomNumbers(4);
    }

    /**
     * 缓存验证码 [过期时间:code] 的格式缓存
     * @param usage 本次缓存的用途，是限制发送，还是验证码
     * @param phone 是手机号，还是ip
     * @param code 验证码
     */
    private void cacheAuthCode(String usage, String phone, String code){
        RList<Object> authCodeList = redissonClient.getList(generateRedisCode(usage, PHONE, phone));
        long expireTime = getExpireTimeStamp(AUTH_CODE_TTL);
        authCodeList.add(timePrefix(expireTime,code));
        authCodeList.expire(Duration.ofMinutes(AUTH_CODE_TTL));
    }

    /**
     * 获取验证码
     * @param usage 本次缓存的用途，是限制发送，还是验证码
     * @param phone 手机号
     * @return
     */
    private List<String> getCacheAuthCode(String usage, String phone){
        String redisCode = generateRedisCode(usage, PHONE, phone);
        RList<Object> authCodes = redissonClient.getList(redisCode);
        Iterator<Object> iterator = authCodes.iterator();
        // 删除所有过期的authCode
        clearExpiredCode(iterator);
        //取出所有的code
        return authCodes.stream()
                .map(value->{
                    String str = (String)value;
                    int divIndex = str.indexOf(':');
                    return str.substring(divIndex+1);
        }).collect(Collectors.toList());
    }

    /**
     * 删除所有过期的authcode
     * @param iterator 迭代器
     */
    void clearExpiredCode(Iterator iterator){
        while(iterator.hasNext()){
            String value = (String)iterator.next();
            int divIndex = value.indexOf(':');
            long expireTime = Long.parseLong(value.substring(0,divIndex));
            if (!expired(expireTime)){
                break;
            }
            iterator.remove();
        }
    }

    /**
     * 判断是否过期
     * @param timestamp1  时间戳
     * @return
     */
    private Boolean expired(long timestamp1){
        DateTime date1 = DateUtil.date(timestamp1);
        DateTime date2 = DateUtil.date();
        return DateUtil.compare(date1, date2)<0;
    }

    /**
     * 删除指定手机号的验证码
     * @param usage
     * @param phone
     */
    private void removeCacheAuthCode(String usage, String phone){
        String redisKey = generateRedisCode(usage,PHONE, phone);
        redissonClient.getList(redisKey).delete();
    }

    /**
     * 给定过期时间 返回过期对应时刻的时间戳
     * @param interval  过期时间 单位：分钟
     * @return
     */
    private Long getExpireTimeStamp(Integer interval){
        return DateUtil.date().offset(MINUTE,interval).getTime();
    }
    /**
     * 限制指定ip和phone在1分钟内不能发送验证码
     * @param usage
     * @param phone
     * @param ip
     */
    private void limitSend(String usage, String phone, String ip){
        String phoneCode = generateRedisCode(usage,PHONE,phone);
        String ipCode = generateRedisCode(usage,IP,ip);
        // 批量执行
        RBatch batch = redissonClient.createBatch();
        RBucketAsync<Object> phoneBucket = batch.getBucket(phoneCode);
        phoneBucket.setAsync(USELESS_VALUE);
        phoneBucket.expireAsync(Duration.ofMinutes(SEND_AUTH_CODE_INTERVAL));
        RBucketAsync<Object> ipBucket = batch.getBucket(ipCode);
        ipBucket.setAsync(USELESS_VALUE);
        ipBucket.expireAsync(Duration.ofMinutes(SEND_AUTH_CODE_INTERVAL));
        batch.execute();
    }

    /**
     * 拼接 时间戳和验证码
     * @param time
     * @param value
     * @return
     */
    private String timePrefix(long time, String value){
        return time+":"+value;
    }
    /**
     * 生成redisCode
     * @param usage
     * @param type
     * @param value
     * @return
     */
    /**
     * 生成redisKey 生成规则 业务名:服务名:用途:类型:值
     * @param usage
     * @param type
     * @param value
     * @return
     */
    private String generateRedisCode(String usage, String type, String value){
        return BIZ_NAME+":"+SERVICE_NAME+":"+usage+":"+type+":"+value;
    }
}
