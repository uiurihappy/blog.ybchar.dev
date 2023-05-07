package com.ybcharlog.api.exception;

import com.ybcharlog.api.Common.constant.ResultCode;
import lombok.Getter;

public class SignInException extends Exception {

    @Getter
    private ResultCode resultCode;

    public SignInException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

}
