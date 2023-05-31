package com.nwt.nwt_projekat_user.services;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.INVALID_LOGIN_DETAILS;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication){
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails userDetails;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(authenticationToken.getPrincipal().toString());
        } catch (UsernameNotFoundException exception) {
            throw new WrappedException(INVALID_LOGIN_DETAILS);
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
