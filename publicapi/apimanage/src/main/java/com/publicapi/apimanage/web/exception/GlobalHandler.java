package com.publicapi.apimanage.web.exception;

import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.common.exception.ApiManageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.Set;

import static com.publicapi.apimanage.common.enums.ErrorResultEnum.PARAMETER_EXCEPTION;
import static com.publicapi.apimanage.common.enums.ErrorResultEnum.SYSTEM_EXCEPTION;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ApiManageException.class)
    public Result<Object> apiResourceExceptionHandler(ApiManageException e){
        e.printStackTrace();
        return Result.fail(e.getErrorCode(),e.getErrorMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> constraintViolationExceptionHandler(ConstraintViolationException e){
        e.printStackTrace();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder errorMessageBuilder = new StringBuilder();
        errorMessageBuilder.append("[");
        constraintViolations.stream().forEach(constraintViolation->{
            errorMessageBuilder.append(constraintViolation.getMessageTemplate());
            errorMessageBuilder.append("、");
        });
        errorMessageBuilder.deleteCharAt(errorMessageBuilder.length()-1);
        errorMessageBuilder.append("]");
        return Result.fail(PARAMETER_EXCEPTION.getErrorCode(),errorMessageBuilder.toString());
    }
    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(Exception e){
        e.printStackTrace();
        return Result.fail(SYSTEM_EXCEPTION.getErrorCode(),SYSTEM_EXCEPTION.getErrorMessage());
    }
}
