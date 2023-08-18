package com.publicapi.apimanage.biz.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.*;
import com.publicapi.apimanage.biz.bo.ApiUser;
import com.publicapi.apimanage.biz.constants.RoleEnum;
import com.publicapi.apimanage.biz.convert.ApiUserConvert;
import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.common.CommonPage;
import com.publicapi.apimanage.common.UserContext;
import com.publicapi.apimanage.common.UserInfo;
import com.publicapi.apimanage.common.exception.ApiManageException;
import com.publicapi.apimanage.common.query.UserPageQuery;
import com.publicapi.apimanage.common.utils.TokenUtil;
import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.service.textmessage.TextMessageService;
import com.publicapi.apimanage.core.service.textmessage.template.AuthCodeTemplateParam;
import com.publicapi.apimanage.core.service.user.UserDomainService;
import com.publicapi.apimanage.web.vo.user.*;
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
import static com.publicapi.apimanage.biz.constants.RoleEnum.USER;
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
        // 校验手机号是否合法
        checkPhoneFormat(phone);
        // 校验IP是否合法
        checkIpFormat(ip);
        // 校验该手机号和ip是否被限制
        checkPhoneLimited(REGISTER_AUTH_CODE_INTERVAL,phone);
        checkIPLimited(REGISTER_AUTH_CODE_INTERVAL,ip);
        // 生成验证码
        String code = generateAuthCode();
        // 将验证码保存到redis
        cacheAuthCode(REGISTER_AUTH_CODE,phone,code);
        //发送消息
        try {
            messageService.sendTextMsg(phone, MessageEnum.REGISTER_AUTH_CODE,new AuthCodeTemplateParam(code));
            // 限制手机号和ip
            limitPhoneSend(REGISTER_AUTH_CODE_INTERVAL,phone);
            limitIPSend(REGISTER_AUTH_CODE_INTERVAL,ip);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("发送短信验证码失败 phone={},code={}",phone,code);
            throw new ApiManageException(SEND_AUTH_CODE_ERROR);
        }
    }

    @Override
    public Boolean sendSensitiveAuthCode() {
        // todo 加锁，使得变成一个原子操作
        String phone = UserContext.get().getPhone();
        // 校验该手机号是否被限制
        checkPhoneLimited(SENSITIVE_AUTH_CODE_INTERVAL, phone);
        // 生成验证码并保存到redis
        String code = generateAuthCode();
        cacheAuthCode(SENSITIVE_AUTH_CODE,phone,code);
        //发送消息
        try {
            messageService.sendTextMsg(phone, MessageEnum.SENSITIVE_AUTH_CODE,new AuthCodeTemplateParam(code));
            // 限制手机号
            limitPhoneSend(SENSITIVE_AUTH_CODE_INTERVAL,phone);
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
        user.setRole(USER.getName());
        user.setNickName(NICK_NAME);
        user.setHeadPhoto(HEAD_PHOTO);
        user.setStatus(ENABLE);
        userDomainService.createUser(user);
        removeCacheAuthCode(REGISTER_AUTH_CODE,registerUserVO.getPhone());
        return true;
    }

    @Override
    public String login(LoginUserVO loginUserVO) {
        ApiUser user = userDomainService.getUserByUsername(loginUserVO.getUsername());
        if (ObjectUtil.isEmpty(user)||!user.getPassword().equals(loginUserVO.getPassword())){
            throw new ApiManageException(USERNAME_OR_PASSWORD_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(user.getUsername());
        userInfo.setRole(user.getRole());
        userInfo.setPhone(user.getPhone());
        return TokenUtil.generate(userInfo);
    }

    @Override
    public Boolean updatePassword(UpdatePasswordVO updatePasswordVO) {
        // 校验用户是否存在
        ApiUser user = userDomainService.getUserByUsername(UserContext.get().getUsername());
        if(ObjectUtil.isEmpty(user)){
            throw new ApiManageException(USER_NOT_EXIST);
        }
        // 校验旧密码是否正确
        if (!user.getPassword().equals(updatePasswordVO.getOldPassword())){
            throw new ApiManageException(PASSWORD_ERROR);
        }
        user.setPassword(updatePasswordVO.getNewPassword());
        return userDomainService.updateUserById(user);
    }

    @Override
    public UserDetailsVO getUser() {
        ApiUser user = userDomainService.getUserByUsername(UserContext.get().getUsername());
        if(ObjectUtil.isEmpty(user)){
            throw new ApiManageException(USER_NOT_EXIST);
        }
        return userConvert.modal2DetailsVo(user);
    }

    @Override
    public Boolean updateUserInfo(UpdateUserInfoVO updateUser) {
        ApiUser user = userDomainService.getUserByUsername(UserContext.get().getUsername());
        if(ObjectUtil.isEmpty(user)){
            throw new ApiManageException(USER_NOT_EXIST);
        }
        if (StrUtil.isNotEmpty(updateUser.getHeadPhoto())){
            user.setHeadPhoto(updateUser.getHeadPhoto());
        }
        if (StrUtil.isNotEmpty(updateUser.getNickName())){
            user.setNickName(updateUser.getNickName());
        }
        return userDomainService.updateUserById(user);
    }

    @Override
    public Boolean updateRole(UpdateRoleVO updateRoleVO) {
        ApiUser user = userDomainService.getUserByUsername(updateRoleVO.getUsername());
        if(ObjectUtil.isEmpty(user)){
            throw new ApiManageException(USER_NOT_EXIST);
        }
        // 校验角色的合法性
        checkRole(updateRoleVO.getRole());
        user.setRole(updateRoleVO.getRole());
        return userDomainService.updateUserById(user);
    }

    @Override
    public CommonPage<UserPageVO> userList(UserPageQuery userPageQuery) {
        CommonPage<ApiUser> userCommonPage = userDomainService.pageUser(userPageQuery);
        return CommonPage.convert(userCommonPage,userConvert.listModal2PageVO(userCommonPage.getLists()));
    }

    @Override
    public UserKeyVO getUserKey(String authCode) {
        // todo 原子操作
        UserInfo userInfo = UserContext.get();
        // 校验验证码
        checkAuthCode(SENSITIVE_AUTH_CODE,userInfo.getPhone(), authCode);
        // 查询当前用户的appKey和appSecret
        ApiUser user = userDomainService.getUserByUsername(userInfo.getUsername());
        // 删除验证码
        removeCacheAuthCode(SENSITIVE_AUTH_CODE,userInfo.getPhone());
        UserKeyVO keyVO = userConvert.modal2KeyVO(user);
        return keyVO;
    }

    @Override
    public UserKeyVO resetUserKey(String authCode) {
        // todo 原子操作
        UserInfo userInfo = UserContext.get();
        // 校验验证码
        checkAuthCode(SENSITIVE_AUTH_CODE,userInfo.getPhone(), authCode);
        // 获取当前用户信息
        ApiUser user = userDomainService.getUserByUsername(userInfo.getUsername());
        // 重新生成appKey和appSecret
        generateAuthentication(user);
        // 保存新的用户认证信息
        userDomainService.updateUserById(user);
        // 删除验证码
        removeCacheAuthCode(SENSITIVE_AUTH_CODE,userInfo.getPhone());
        return userConvert.modal2KeyVO(user);
    }

    void checkPhoneFormat(String phone){
        if (!ReUtil.isMatch(PHONE_REGULAR,phone)){
            throw new ApiManageException(PHONE_INVALID);
        }
    }
    void checkIpFormat(String ip){
        if (!ReUtil.isMatch(IP_REGULAR,ip)){
            throw new ApiManageException(IP_INVALID);
        }
    }
    private void checkRole(String roleName){
        for(RoleEnum role:RoleEnum.values()){
            if (role.getName().equals(roleName))
                return;
        }
        throw new ApiManageException(ROLE_NOT_EXIST);
    }
    private void checkAuthCode(String usage, String phone, String authCode){
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
     */
    private void checkPhoneLimited(String usage, String phone){
        String phoneCode = generateRedisCode(usage,PHONE,phone);
        RBucket<Object> bucket = redissonClient.getBucket(phoneCode);

        if (ObjectUtil.isNotNull(bucket.get())){
            throw new ApiManageException(SEND_AUTH_CODE_IS_LIMITED);
        }
    }

    private void checkIPLimited(String usage, String ip){
        String ipCode = generateRedisCode(usage,IP,ip);
        RBucket<Object> bucket = redissonClient.getBucket(ipCode);
        if (ObjectUtil.isNotNull(bucket.get())){
            throw new ApiManageException(SEND_AUTH_CODE_IS_LIMITED);
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
     * 限制指定phone在1分钟内不能发送验证码
     * @param usage
     * @param phone
     */
    private void limitPhoneSend(String usage, String phone){
        String phoneCode = generateRedisCode(usage,PHONE,phone);
        // 批量执行
        RBatch batch = redissonClient.createBatch();
        RBucketAsync<Object> phoneBucket = batch.getBucket(phoneCode);
        phoneBucket.setAsync(USELESS_VALUE);
        phoneBucket.expireAsync(Duration.ofMinutes(SEND_AUTH_CODE_INTERVAL));
        batch.execute();
    }
    private void limitIPSend(String usage, String ip){
        String ipCode = generateRedisCode(usage,IP,ip);
        // 批量执行
        RBatch batch = redissonClient.createBatch();
        RBucketAsync<Object> phoneBucket = batch.getBucket(ipCode);
        phoneBucket.setAsync(USELESS_VALUE);
        phoneBucket.expireAsync(Duration.ofMinutes(SEND_AUTH_CODE_INTERVAL));
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
