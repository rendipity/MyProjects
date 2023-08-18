package com.publicapi.apimanage.web.base.aop;

import com.publicapi.apimanage.biz.constants.RoleEnum;
import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.common.UserContext;
import com.publicapi.apimanage.common.UserInfo;
import com.publicapi.apimanage.common.enums.ErrorResultEnum;
import com.publicapi.apimanage.web.exception.AccessControl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccessAspect {
    @Around("@annotation(com.publicapi.apimanage.web.exception.AccessControl)&&@annotation(accessControl)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, AccessControl accessControl){
        UserInfo userInfo = UserContext.get();
        RoleEnum roleEnum = RoleEnum.getByName(userInfo.getRole());
        RoleEnum needAuthority = accessControl.value();
        // 权限级别越高，value值越小
        if (roleEnum.getValue()>needAuthority.getValue()){
            return Result.fail(ErrorResultEnum.ACCESS_FORBIDDEN);
        }
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
