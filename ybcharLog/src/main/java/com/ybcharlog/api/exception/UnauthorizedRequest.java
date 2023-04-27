package com.ybcharlog.api.exception;

public class UnauthorizedRequest extends YbcharLogException{
	private static final String MESSAGE = "인증이 필요합니다.";

	public UnauthorizedRequest() {
		super(MESSAGE);
	}

	public UnauthorizedRequest(String fieldName, String message) {
		super(MESSAGE);
		addValidations(fieldName, message);
	}

	@Override
	public int statusCode() {
		return 401;
	}
}
