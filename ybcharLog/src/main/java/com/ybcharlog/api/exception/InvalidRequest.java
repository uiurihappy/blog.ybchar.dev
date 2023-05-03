package com.ybcharlog.api.exception;

import lombok.Getter;

/**
 * status: 400
 */
@Getter
public class InvalidRequest extends YbcharLogException{

	private static final String MESSAGE = "잘못된 요청입니다.";

	public String fieldName;
	public String message;

	public InvalidRequest() {
		super(MESSAGE);
	}

	public InvalidRequest(String fieldName, String message) {
		super(MESSAGE);
		addValidations(fieldName, message);
	}

	@Override
	public int statusCode() {
		return 400;
	}
}
