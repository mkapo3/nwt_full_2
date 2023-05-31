package com.nwt.nwt_projekat_user.repository.user;

import com.nwt.nwt_projekat_user.models.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDataService {

    @Autowired
    CustomUserRepository customUserRepository;

    public CustomUser getUserById(Long id){
        Optional<CustomUser> customUser = customUserRepository.findById(id);
        return customUser.orElse(null);
    }

    public CustomUser getUserByEmail(String email){
        Optional<CustomUser> customUser = customUserRepository.findByEmail(email);
        return customUser.orElse(null);
    }

    public void createOrUpdateUser(CustomUser customUser){
        customUserRepository.save(customUser);
    }

}

