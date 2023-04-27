package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;

	@PostMapping("/login")
	public void login(@RequestBody LoginDto loginDto) {
		// json 아이디/ 비밀번호
		log.info(">>> login {} ", loginDto);

		// DB 에서 조회

		// 토근을 응답
	}
}
