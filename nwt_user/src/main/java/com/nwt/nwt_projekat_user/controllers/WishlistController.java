package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.*;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.repository.wishlist.WishlistDataService;
import com.nwt.nwt_projekat_user.services.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/user/wishlist")
public class WishlistController {

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    WishlistDataService wishlistDataService;

    @GetMapping("")
    @ResponseBody
    public Wishlist getCurrentUserWishlist(){
        CustomUser customUser = customUserDataService.getUserByEmail(currentUserService.getEmail());
        return customUser.getWishlist();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public WishlistProduct addCartProduct(@PathVariable Long id, @RequestBody WishlistProduct data){
        CustomUser customUser = customUserDataService.getUserByEmail(currentUserService.getEmail());
        Wishlist wishlist = customUser.getWishlist();
        wishlist.getWishlistProducts().add(data);
        wishlistDataService.createOrUpdateWishlist(wishlist);
        return data;
    }


}
