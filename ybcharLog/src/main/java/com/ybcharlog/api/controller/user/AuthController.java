package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.ResponseDto.auth.SessionResponse;
import com.ybcharlog.api.config.AppConfig;
import com.ybcharlog.api.service.auth.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	@Value("${ybchar.host}")
	private String hostname;

	private final AuthService authService;
	private final AppConfig appConfig;

	@PostMapping("/login")
	public SessionResponse login(@RequestBody LoginDto loginDto) {
		Long userId = authService.signIn(loginDto);

		SecretKey key = Keys.hmacShaKeyFor(appConfig.getSecretKey());
		String jwts = Jwts.builder()
				.setSubject(String.valueOf(userId))
				.signWith(key)
				.setIssuedAt(new Date(System.currentTimeMillis())) // 토큰발행일자
				.setExpiration(new Date(System.currentTimeMillis()+ 60 * 10000 * 6)) // 토큰유효기간(1시간)
				.compact();

		return new SessionResponse(jwts);
	}
}
