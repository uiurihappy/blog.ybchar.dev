package com.ybcharlog.api.exception;

import lombok.Getter;

/**
 * status: 404
 */
@Getter
public class UserNotFound extends YbcharLogException{

	private static final String MESSAGE = "존재하지 않는 사용자입니다.";

	public UserNotFound() {
		super(MESSAGE);
	}

	@Override
	public int statusCode() {
		return 404;
	}
}
