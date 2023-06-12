package com.ybcharlog.api.service.user;

import com.ybcharlog.api.Common.constant.ResultCode;
import com.ybcharlog.api.RequestDto.UserDto.SignInReq;
import com.ybcharlog.api.RequestDto.UserDto.SignInRes;
import com.ybcharlog.api.RequestDto.auth.SignUpDto;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.CustomException;
import com.ybcharlog.api.exception.UnauthorizedRequest;
import com.ybcharlog.api.repository.user.UserRepository;
import com.ybcharlog.api.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${util.jwt.defaultExpirationMinutes}")
    private Long accessTokenExpirationMinutes;

    @Value("${util.jwt.defaultRefreshTokenMinutes}")
    private Long refreshTokenExpirationMinutes;

//    private final CommonUserService commonUserService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpDto req) {
//        boolean exists = commonUserService.existsEmail(req.getEmail());
        boolean exists = userRepository.findByEmail(req.getEmail()).isPresent();
        if (exists) {
            throw new CustomException(ResultCode.EXISTS_EMAIL);
        }

        User user = User.initEmailUser(req.getEmail(),
                passwordEncoder.encode(req.getPassword()), req.getNickname(), req.getRoles());
        userRepository.save(user);
    }

    @Transactional
    public SignInRes signIn(SignInReq req) {
        boolean exists = userRepository.findByEmail(req.getEmail()).isPresent();
        if (!exists) {
            throw new CustomException(ResultCode.NOT_EXISTS_EMAIL);
        }

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Not exists user. email: " + req.getEmail()));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UnauthorizedRequest(
                    String.format("Not Authorized.(email: %s)", req.getEmail()));
        }
        log.info(user.getEmail(), user.getRoles(), user.getPassword());

        return this.generateToken(user);
    }

    private SignInRes generateToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Duration expiresIn = Duration.between(now, now.plusMinutes(accessTokenExpirationMinutes));
        Duration refreshExpiresIn =
                Duration.between(now, now.plusMinutes(refreshTokenExpirationMinutes));
        return SignInRes.builder()
                .accessToken(TokenService.generateToken(user, accessTokenExpirationMinutes))
                .refreshToken(TokenService.generateRefreshToken(user, refreshTokenExpirationMinutes))
                .userRoles(user.getRoles())
                .expiresIn(expiresIn.toMillis())
                .refreshExpiresIn(refreshExpiresIn.toMillis())
                .build();
    }
}
