package com.ybcharlog.api.resolver;

import com.ybcharlog.api.config.data.UserSession;
import com.ybcharlog.api.domain.auth.Session;
import com.ybcharlog.api.exception.UnauthorizedRequest;
import com.ybcharlog.api.repository.user.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

	private final SessionRepository sessionRepository;
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
		Session userSession = sessionRepository.findByAccessToken(accessToken)
				.orElseThrow(UnauthorizedRequest::new);

		return new UserSession(userSession.getUser().getId());
	}
}
