package com.publicapi.apimanage.common;

import lombok.Data;

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
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public static <T> Result<T> success(T data){
        return new Result<>(true,data);
    }

    public static <T> Result<T> fail(String errorCode, String errorMsg){
        return new Result<>(errorCode,errorMsg);
    }
}
