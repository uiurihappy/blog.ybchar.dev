package com.ybcharlog.api.config;

import com.ybcharlog.api.interceptor.AuthInterceptor;
import com.ybcharlog.api.repository.user.SessionRepository;
import com.ybcharlog.api.resolver.AuthResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {


	private final SessionRepository sessionRepository;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthResolver(sessionRepository));
	}
}