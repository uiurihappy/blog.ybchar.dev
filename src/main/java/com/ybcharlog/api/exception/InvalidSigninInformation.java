package com.ybcharlog.api.exception;

public class InvalidSigninInformation extends YbcharLogException{

	private static final String MESSAGE = "아이디 또는 비밀번호가 잘못되었습니다.";

	public InvalidSigninInformation() {
		super(MESSAGE);
	}

	public InvalidSigninInformation(String message, Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int statusCode() {
		return 400;
	}
}
