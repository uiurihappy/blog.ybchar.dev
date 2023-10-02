package com.ybcharlog.api.exception;

import com.ybcharlog.api.common.constant.ResultCode;

public class AlreadyExistsEmailException extends YbcharLogException{

    private static String MESSAGE = "이미 가입된 회원입니다.";

    public AlreadyExistsEmailException(ResultCode notExistsEmail){
        super(MESSAGE);
    }

    public AlreadyExistsEmailException(String message) {
        super(message);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
