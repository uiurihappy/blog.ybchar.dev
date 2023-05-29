package com.ybcharlog.api.service.user;

import com.ybcharlog.api.Common.constant.ResultCode;
import com.ybcharlog.api.RequestDto.UserDto.SignInReq;
import com.ybcharlog.api.RequestDto.UserDto.SignInRes;
import com.ybcharlog.api.RequestDto.auth.SignUpDto;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.CustomException;
import com.ybcharlog.api.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final CommonUserService commonUserService;

    @Transactional
    public void signUp(SignUpDto req) {
        boolean exists = commonUserService.existsEmail(req.getEmail());
        if (exists) {
            throw new CustomException(ResultCode.EXISTS_EMAIL);
        }

        User user = User.initEmailUser(req.getEmail(),
                commonUserService.encryptPassword(req.getPassword()), req.getNickname());
        commonUserService.add(user);
    }

    @Transactional
    public SignInRes signIn(SignInReq req) {
        boolean exists = commonUserService.existsEmail(req.getEmail());
        if (!exists) {
            throw new CustomException(ResultCode.NOT_EXISTS_EMAIL);
        }

        User user = commonUserService.getUser(req.getEmail(), req.getPassword());


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
