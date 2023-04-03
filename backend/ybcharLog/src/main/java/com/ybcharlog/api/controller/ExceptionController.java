package com.ybcharlog.api.controller;


import com.ybcharlog.api.ResponseDto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseBody
	public ErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
//		MethodArgumentNotValidException
//		e.getField()
		log.info("exceptionHandler error", e);

		if (e.hasErrors()) {
//			FieldError fieldError = e.getFieldError();
//			String field = fieldError.getField();
//			String message = fieldError.getDefaultMessage();
//		Map<String, String> response = new HashMap<>();
//
//		response.put(field, message);
			ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");
			for (FieldError fieldError : e.getFieldErrors())
				response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());

			return response;
		} else {
			// 에러가 없는 경우
			return null;
		}
//		return response;
//		return null;
	}
}
