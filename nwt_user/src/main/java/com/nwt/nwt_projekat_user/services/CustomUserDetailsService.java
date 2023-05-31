package com.nwt.nwt_projekat_user.services;

import com.nwt.nwt_projekat_user.error.ErrorConstants;
import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.INVALID_LOGIN_DETAILS;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomUserDataService customUserDataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUser customUser = customUserDataService.getUserByEmail(username);

        if(customUser == null){
            throw new WrappedException(INVALID_LOGIN_DETAILS);
        }

        Set<GrantedAuthority> authorities = customUser
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new User(customUser.getEmail(), customUser.getPassword(), authorities);
    }
}
