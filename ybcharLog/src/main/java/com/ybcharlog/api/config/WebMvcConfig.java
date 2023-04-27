package com.ybcharlog.api.config;

import com.ybcharlog.api.interceptor.AuthInterceptor;
import com.ybcharlog.api.resolver.AuthResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new AuthInterceptor())
//				.excludePathPatterns("/error","/favicon.ico", "/authTest","/posts/list", "/posts/{postId}", "/posts/thumbnail/image"
//				,"/file/images");
//	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthResolver());
//		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
}
