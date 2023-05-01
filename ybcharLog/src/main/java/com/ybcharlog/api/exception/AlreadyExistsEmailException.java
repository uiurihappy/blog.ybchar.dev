package com.ybcharlog.api.exception;

public class AlreadyExistsEmailException extends YbcharLogException{

    private static String MESSAGE = "이미 가입된 회원입니다.";

    public AlreadyExistsEmailException(){
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
