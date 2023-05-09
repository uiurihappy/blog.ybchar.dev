package com.ybcharlog.api.config;

import com.ybcharlog.api.filter.ExceptionHandlerFilter;
import com.ybcharlog.api.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final TokenFilter tokenFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(
                "/auth/join",
                "/auth/login",
                "/sound"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //String[] permitAllUrl = {"/**"};
        // 참고 레퍼런스: https://velog.io/@pjh612/Deprecated%EB%90%9C-WebSecurityConfigurerAdapter-%EC%96%B4%EB%96%BB%EA%B2%8C-%EB%8C%80%EC%B2%98%ED%95%98%EC%A7%80
        String[] permitAllUrl = {
                "/favicon.ico", "/robots.txt", "/fonts/**", "/css/**", "/images/**", "/js/**",
                //"/test/**",
                "/enums/**", "/auth/join", "/join/verification-url", "/auth/login",
                "/posts/save",
                "/users/nickname/exists",
                "/view/users/change-password",
                "/users/password",
        };

       return httpSecurity
               .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(permitAllUrl).permitAll()
                .anyRequest().authenticated()
                   .and()
                   .logout()
                   .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                   .logoutSuccessUrl("/api/posts/list?page=1&size=12")
                   .invalidateHttpSession(true)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
               .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
               .addFilterBefore(exceptionHandlerFilter, TokenFilter.class)
               .build();
    }

}