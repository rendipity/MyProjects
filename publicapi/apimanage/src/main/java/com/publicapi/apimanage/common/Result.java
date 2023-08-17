package com.publicapi.apimanage.common;

import com.publicapi.apimanage.common.enums.ErrorResultEnum;
import lombok.Data;

import java.sql.Statement;

@Data
public class Result <T> {

    private Boolean success;
    private String errorCode;
    private String errorMsg;
    private T data;

    public Result(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(String errorCode, String errorMsg) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public static <T> Result<T> success(T data){
        return new Result<>(true,data);
    }

    public static  Result<Object> fail(String errorCode, String errorMsg){
        return new Result<>(errorCode,errorMsg);
    }
    public static  Result<Object> fail(ErrorResultEnum resultEnum){
        return new Result<>(resultEnum.getErrorCode(), resultEnum.getErrorMessage());
    }
}
