package com.nwt.nwt_projekat_user.services;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
//@SessionScope
public class CurrentUserService {

    private String email;

    private Long id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
