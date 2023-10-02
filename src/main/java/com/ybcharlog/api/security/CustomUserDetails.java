package com.ybcharlog.api.security;

import com.google.common.base.Strings;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String userEmail;
    private final String userNickname;
    private final List<String> userRoleList;
    private final List<GrantedAuthority> authorityList;

    public CustomUserDetails(Long userId, String userEmail, String userNickname,
        String userRoles) {
        this.userId = userId;
        this.authorityList = new ArrayList<>();
        this.userRoleList = new ArrayList<>();
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        if (!Strings.isNullOrEmpty(userRoles)) {
            String[] roleNames = userRoles.split(",");
            for (String roleName : roleNames) {
                this.authorityList.add(new SimpleGrantedAuthority(roleName));
                this.userRoleList.add(roleName);
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
