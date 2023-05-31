package com.example.nwt_api_gateway.req_res;

import com.example.nwt_api_gateway.model.Authority;

import java.util.List;

public class ValidateTokenResponse {

    private List<Authority> authorities;

    private String email;

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
