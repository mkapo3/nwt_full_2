package com.nwt.nwt_projekat_user.repository.role;

import com.nwt.nwt_projekat_user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findById(Long id);
    public Optional<Role> findByName(String name);

}
