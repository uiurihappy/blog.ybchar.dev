package com.ybcharlog.api.service.auth;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.ResponseDto.auth.SessionResponse;
import com.ybcharlog.api.domain.auth.Session;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.InvalidSigninInformation;
import com.ybcharlog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	@Transactional
	public Long signIn(LoginDto loginDto) {
		User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
				.orElseThrow(InvalidSigninInformation::new);
		return user.getId();
	}
}
