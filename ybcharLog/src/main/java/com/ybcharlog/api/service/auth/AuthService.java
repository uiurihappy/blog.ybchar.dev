package com.ybcharlog.api.service.auth;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.InvalidSigninInformation;
import com.ybcharlog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	public User signIn(LoginDto loginDto) {
		User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
				.orElseThrow(InvalidSigninInformation::new);
		user.addSession();
		return user;
	}
}
