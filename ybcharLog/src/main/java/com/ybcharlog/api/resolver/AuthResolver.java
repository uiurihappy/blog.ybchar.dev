package com.ybcharlog.api.resolver;

import com.ybcharlog.api.config.data.UserSession;
import com.ybcharlog.api.exception.UnauthorizedRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserSession.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String accessToken = webRequest.getHeader("authorization");
		if (accessToken == null || accessToken.equals("")) {
			throw new UnauthorizedRequest();
		}

		// 데이터베이스 사용자 확인작업
		return new UserSession((long) Integer.parseInt(accessToken));
	}
}
