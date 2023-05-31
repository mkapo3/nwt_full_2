package com.nwt.nwt_projekat_user.repository.wishlist;

import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    public Wishlist getById(Long id);

}
