package com.nwt.nwt_projekat_user.repository.user;

import com.nwt.nwt_projekat_user.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    public Optional<CustomUser> findById(Long id);
    public Optional<CustomUser> findByEmail(String email);

}
