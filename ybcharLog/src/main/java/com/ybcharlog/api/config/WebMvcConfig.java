package com.ybcharlog.api.config;

import com.ybcharlog.api.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor())
				.excludePathPatterns("/authTest", "/authTest2","/posts/list", "/posts/{postId}", "/posts/thumbnail/image"
				,"/file/images");
	}
}
