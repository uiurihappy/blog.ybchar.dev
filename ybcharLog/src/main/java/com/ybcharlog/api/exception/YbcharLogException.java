package com.ybcharlog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// 최상위 Exception
@Getter
public abstract class YbcharLogException extends RuntimeException {

	public final Map<String, String> validation = new HashMap<>();

	public YbcharLogException(String message) {
		super(message);
	}

	public YbcharLogException(String message, Throwable cause) {
		super(message, cause);
	}

	public abstract int statusCode();

	public void addValidations(String fieldName, String message) {
		validation.put(fieldName, message);
	};
}
