package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.ResponseDto.auth.SessionResponse;
import com.ybcharlog.api.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	@Value("${ybchar.host}")
	private String hostname;

	private final AuthService authService;
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
		SessionResponse sessionResponse = authService.signIn(loginDto);
		ResponseCookie responseCookie = ResponseCookie.from("SESSION", sessionResponse.getAccessToken())
				.domain(hostname)
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofDays(30))
				.sameSite("Strict")
				.build();

		log.info(">>>> cookie = {}", responseCookie);

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
	}
}
