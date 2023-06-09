package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.SignUpDto;
import com.ybcharlog.api.config.AppConfig;
import com.ybcharlog.api.repository.user.UserRepository;
import com.ybcharlog.api.service.auth.AuthService;
import com.ybcharlog.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ybcharlog.api.RequestDto.UserDto.SignInReq;
import static com.ybcharlog.api.RequestDto.UserDto.SignInRes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	/** 관리자 전용으로 사용할 controller
	 * Admin은 개발자인 나 혼자 사용으로 클라이언트 사이드에서 회원가입 로직은 없도록 할 것이다.
	 * 따라서 로그인 페이지에서 실제로 로그인 되는 사람은 나 혼자이다.
	 * 만약 누군가 회원가입 로직을 타서 사용해도 ROLE_ADMIN 권한이 없기에 함부로 글 삭제 및 수정이 안됨
	 */

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto req) {
		userService.signUp(req);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<SignInRes> signIn(@Valid @RequestBody SignInReq req) {
		return ResponseEntity.ok(userService.signIn(req));
	}
}
