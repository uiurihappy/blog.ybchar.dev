package com.ybcharlog.api.exception;

import com.ybcharlog.api.Common.constant.ResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    public CustomException() {
    }

    public CustomException(Throwable throwable) {
        super(throwable);
    }

    public CustomException(String message) {
        super(message);
        this.resultCode = ResultCode.NONE;
    }

    public CustomException(ResultCode code) {
        super(code.getDesc());
        this.resultCode = code;
    }

    public CustomException(ResultCode code, String message) {
        super(message);
        this.resultCode = code;
    }
}

