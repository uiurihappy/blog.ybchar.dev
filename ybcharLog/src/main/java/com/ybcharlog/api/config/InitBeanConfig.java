package com.ybcharlog.api.config;

import com.ybcharlog.api.Common.util.EncryptUtil;
import com.ybcharlog.api.Common.util.JwtUtil;
import com.ybcharlog.api.Common.util.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;

@Configuration
@RequiredArgsConstructor
public class InitBeanConfig {

    @Value("${util.encrypt.secretKey}")
    private String encSecretKey;

    @Value("${util.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${util.jwt.refreshKey}")
    private String jwtReFreshKey;

    private final Environment env;

    @Bean
    public ProfileUtil profileUtil() {
        return new ProfileUtil(this.env);
    }

    @Bean
    public EncryptUtil encryptUtil() throws UnsupportedEncodingException {
        return new EncryptUtil(encSecretKey);
    }

    @Bean
    public JwtUtil jwtUtil() throws UnsupportedEncodingException {
        return new JwtUtil(jwtSecretKey, jwtReFreshKey);
    }

}
