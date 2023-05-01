package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.RequestDto.auth.SignUpDto;
import com.ybcharlog.api.ResponseDto.auth.SessionResponse;
import com.ybcharlog.api.config.AppConfig;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.AlreadyExistsEmailException;
import com.ybcharlog.api.exception.InvalidRequest;
import com.ybcharlog.api.repository.user.UserRepository;
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
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	@Value("${ybchar.host}")
	private String hostname;

	private final AuthService authService;
	private final AppConfig appConfig;
	private final UserRepository userRepository;

	@PostMapping("/join")
	public void signUp(@RequestBody SignUpDto signUpDto) {
		Optional<User> isUser = userRepository.findByEmail(signUpDto.getEmail());
		if (isUser.isPresent()) {
			throw new AlreadyExistsEmailException();
		}

		SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
				16
				, 8
				, 1
				, 32
				, 64);

		String encryptedPassword = encoder.encode(signUpDto.getPassword());

		User user = User.builder()
				.nickname(signUpDto.getNickname())
				.email(signUpDto.getEmail())
				.password(encryptedPassword)
				.role(signUpDto.getRole())
				.build();
		userRepository.save(user);
	}

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
