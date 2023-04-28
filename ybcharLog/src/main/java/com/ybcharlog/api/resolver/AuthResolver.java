package com.ybcharlog.api.resolver;

import com.ybcharlog.api.config.data.UserSession;
import com.ybcharlog.api.domain.auth.Session;
import com.ybcharlog.api.exception.UnauthorizedRequest;
import com.ybcharlog.api.repository.user.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

	private final SessionRepository sessionRepository;
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserSession.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		if (servletRequest == null) {
			log.error("servletRequest is null");
			throw new UnauthorizedRequest();
		}
		Cookie[] cookies = servletRequest.getCookies();

		if (cookies.length == 0) {
			log.error("cookies is null");
			throw new UnauthorizedRequest();
		}

		String accessToken = cookies[0].getValue();

		Session userSession = sessionRepository.findByAccessToken(accessToken)
				.orElseThrow(UnauthorizedRequest::new);

		return new UserSession(userSession.getUser().getId());
	}
}
