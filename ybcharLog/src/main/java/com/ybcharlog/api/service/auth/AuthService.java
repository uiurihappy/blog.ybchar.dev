package com.ybcharlog.api.service.auth;

import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.crypto.PasswordEncoder;
import com.ybcharlog.api.crypto.ScryptPasswordEncoder;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.InvalidSigninInformation;
import com.ybcharlog.api.exception.UserNotFound;
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
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long signIn(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(UserNotFound::new);


		boolean isMatch = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
		if (!isMatch){
			throw new InvalidSigninInformation();
		}

		return user.getId();
	}
}
