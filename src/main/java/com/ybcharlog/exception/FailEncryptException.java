package com.ybcharlog.exception;

public class FailEncryptException extends RuntimeException {

    public FailEncryptException() {
    }

    public FailEncryptException(Throwable throwable) {
        super(throwable);
    }

    public FailEncryptException(String message) {
        super(message);
    }
}
