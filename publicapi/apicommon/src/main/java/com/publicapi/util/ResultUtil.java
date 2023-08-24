package com.publicapi.util;


import cn.hutool.core.lang.Assert;
import com.publicapi.exception.ApiManageException;
import com.publicapi.modal.Result;

import static com.publicapi.enums.ErrorResultEnum.RPC_SERVICE_EXCEPTION;

public class ResultUtil {

    public static <T> T isSuccess(Result<T> result){
        Assert.isTrue(result.getSuccess(),()->new ApiManageException(RPC_SERVICE_EXCEPTION));
        return result.getData();
    }
}
