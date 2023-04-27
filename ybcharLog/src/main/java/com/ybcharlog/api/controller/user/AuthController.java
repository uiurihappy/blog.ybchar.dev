package com.ybcharlog.api.controller.user;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.InvalidRequest;
import com.ybcharlog.api.exception.InvalidSigninInformation;
import com.ybcharlog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;

	@PostMapping("/login")
	public User login(@RequestBody LoginDto loginDto) {
		// json 아이디/ 비밀번호
		log.info(">>> login {} ", loginDto);

		// DB 에서 조회
		User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
				.orElseThrow(InvalidSigninInformation::new);
		// 토근을 응답
		return user;
	}
}
