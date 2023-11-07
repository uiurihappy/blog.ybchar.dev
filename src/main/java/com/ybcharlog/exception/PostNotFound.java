package com.ybcharlog.exception;

import lombok.Getter;

/**
 * status: 404
 */
@Getter
public class PostNotFound extends YbcharLogException{

	private static final String MESSAGE = "존재하지 않는 글입니다.";

	public PostNotFound() {
		super(MESSAGE);
	}

	@Override
	public int statusCode() {
		return 404;
	}
}
