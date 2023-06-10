package com.ybcharlog.api.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrinciple extends User {

    private final Long userId;

    public UserPrinciple(com.ybcharlog.api.domain.user.User user){
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
