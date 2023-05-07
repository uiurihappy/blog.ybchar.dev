package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.config.AppConfig;
import com.ybcharlog.api.crypto.PasswordEncoder;
import com.ybcharlog.api.repository.user.UserRepository;
import com.ybcharlog.api.service.auth.AuthService;
import com.ybcharlog.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ybcharlog.api.RequestDto.UserDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	@Value("${ybchar.host}")
	private String hostname;

	private final AuthService authService;
	private final AppConfig appConfig;
	private final UserService userService;
	private final UserRepository userRepository;

	@PostMapping("/join")
	public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReq req) {
		userService.signUp(req);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<SignInRes> signIn(@Valid @RequestBody SignInReq req) {

		return ResponseEntity.ok(userService.signIn(req));
	}
}
