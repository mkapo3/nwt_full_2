package com.nwt.nwt_projekat_user.request_response.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ValidateTokenResponse {

    private Collection<? extends GrantedAuthority> authorities;

    private String email;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
