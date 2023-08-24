package com.publicapi.apimanage.web.exception;


import com.publicapi.apimanage.biz.constants.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessControl {

    RoleEnum value();

}
