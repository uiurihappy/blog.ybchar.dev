package com.ybcharlog.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${frontEnd.port}")
	private Long portNumber;

	@Value("${cors.s3-url}")
	private String s3Url;

	@Value("${cors.sub-prod-url}")
	private String subProdUrl;

	@Value("${cors.sub-api-url}")
	private String subApiUrl;

	@Value("${cors.prod-url}")
	private String prodUrl;

	@Value("${cors.api-url}")
	private String apiUrl;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:" + portNumber, "http://localhost:8081",
						"http://localhost:8080", "http://localhost:9000", s3Url,
						 apiUrl)
				.allowedOriginPatterns(subProdUrl, prodUrl, subApiUrl)
				.allowedMethods(Arrays.stream(HttpMethod.values())
						.map(HttpMethod::name)
						.toArray(String[]::new))
				.allowedHeaders("Authorization", "Content-Type")
				.exposedHeaders("Custom-Header")
				.allowCredentials(true)
				.allowedHeaders("*");
	}
}
