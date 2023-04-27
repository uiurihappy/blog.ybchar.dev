package com.ybcharlog.api.interceptor;

import com.ybcharlog.api.exception.UnauthorizedRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

	@Value("${auth.key}")
	private String authKey;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info(">> preHandle");
		String accessToken = request.getParameter("accessToken");
		if (accessToken != null && !accessToken.equals("")) {
			request.setAttribute("username", accessToken);
			return true;
		}
		throw new UnauthorizedRequest();
//		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info(">> postHandle");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info(">> afterHandle");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
