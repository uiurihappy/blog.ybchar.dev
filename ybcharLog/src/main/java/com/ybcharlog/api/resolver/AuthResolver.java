package com.ybcharlog.api.resolver;

import com.ybcharlog.api.config.AppConfig;
import com.ybcharlog.api.config.data.UserSession;
import com.ybcharlog.api.domain.auth.Session;
import com.ybcharlog.api.exception.UnauthorizedRequest;
import com.ybcharlog.api.repository.user.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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
	private final AppConfig appConfig;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserSession.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		log.info(">>> {}", appConfig.toString());

		String accessToken = webRequest.getHeader("token");
		if (accessToken == null || accessToken.equals(""))
			throw new UnauthorizedRequest();

		try {
			Jws<Claims> claims = Jwts.parserBuilder()
					.setSigningKey(appConfig.getSecretKey())
					.build()
					.parseClaimsJws(accessToken);
			String userId = claims.getBody().getSubject();
			return new UserSession(Long.parseLong(userId));
		} catch (JwtException e){
			throw new UnauthorizedRequest();
		}
	}
}
