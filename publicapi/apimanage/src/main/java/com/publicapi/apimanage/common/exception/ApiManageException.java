package com.publicapi.apimanage.common.exception;

import com.publicapi.apimanage.common.enums.ErrorResultEnum;
import lombok.Data;

@Data
public class ApiManageException extends RuntimeException{

    private String errorCode;
    private String errorMessage;

    public ApiManageException(ErrorResultEnum errorResultEnum) {
        // todo 如果不super会发生什么
        super(errorResultEnum.getErrorMessage());

        this.errorCode = errorResultEnum.getErrorCode();
        this.errorMessage = errorResultEnum.getErrorMessage();
    }
}
