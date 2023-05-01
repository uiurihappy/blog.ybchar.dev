package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.ResponseDto.auth.SessionResponse;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	@Value("${ybchar.host}")
	private String hostname;

	private static final String KEY = "xmJ7Jufnkof80jJgmMrDEfsVjg5UVhx35S2327uJbiI=";


	private final AuthService authService;

	@PostMapping("/login")
	public SessionResponse login(@RequestBody LoginDto loginDto) {
		Long userId = authService.signIn(loginDto);
//		ResponseCookie responseCookie = ResponseCookie.from("SESSION", sessionResponse.getAccessToken())
//				.domain(hostname)
//				.path("/")
//				.httpOnly(true)
//				.secure(false)
//				.maxAge(Duration.ofDays(30))
//				.sameSite("Strict")
//				.build();

		SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));
		String jwts = Jwts.builder()
				.setSubject(String.valueOf(userId))
				.signWith(key)
				.compact();

		return new SessionResponse(jwts);
//		log.info(">>>> cookie = {}", responseCookie);
//		return jwts;
//		return ResponseEntity.ok()
//				.header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
	}
}
