package com.nwt.nwt_projekat_user.repository.wishlist;

import com.nwt.nwt_projekat_user.models.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistDataService {

    @Autowired
    WishlistRepository wishlistRepository;

    public Wishlist getWishlistById(Long id){
        return wishlistRepository.getById(id);
    }

    public void createOrUpdateWishlist(Wishlist customUser){
        wishlistRepository.save(customUser);
    }

}

