package com.ybcharlog.api.service.auth;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.ResponseDto.auth.SessionResponse;
import com.ybcharlog.api.crypto.PasswordEncoder;
import com.ybcharlog.api.domain.auth.Session;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.InvalidSigninInformation;
import com.ybcharlog.api.exception.UserNotFound;
import com.ybcharlog.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	@Transactional
	public Long signIn(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(UserNotFound::new);

		PasswordEncoder encoder = new PasswordEncoder();

		boolean isMatch = encoder.matches(loginDto.getPassword(), user.getPassword());
		if (!isMatch){
			throw new InvalidSigninInformation();
		}

		return user.getId();
	}
}
