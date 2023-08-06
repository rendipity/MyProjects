package com.publicapi.apimanage.web.exception;

import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.common.exception.ApiResourceException;
import org.springframework.web.bind.annotation.*;

import static com.publicapi.apimanage.common.enums.ErrorResultEnum.SYSTEM_EXCEPTION;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ApiResourceException.class)
    public Result<Object> apiResourceExceptionHandler(ApiResourceException e){
        e.printStackTrace();
        return Result.fail(e.getErrorCode(),e.getErrorMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(Exception e){
        e.printStackTrace();
        return Result.fail(SYSTEM_EXCEPTION.getErrorCode(),SYSTEM_EXCEPTION.getErrorMessage());
    }
}
