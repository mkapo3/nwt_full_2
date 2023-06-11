package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.models.Wishlist;
import com.nwt.nwt_projekat_user.models.WishlistProduct;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.repository.wishlist.WishlistDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/wishlist")
public class WishlistController {

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    WishlistDataService wishlistDataService;

    @GetMapping("{email}")
    @ResponseBody
    public Wishlist getCurrentUserWishlist(@PathVariable String email){
        CustomUser customUser = customUserDataService.getUserByEmail(email);
        return customUser.getWishlist();
    }

    @PostMapping("/{email}")
    @ResponseBody
    public WishlistProduct addWishlsitProduct(@PathVariable String email, @RequestBody WishlistProduct data){
        CustomUser customUser = customUserDataService.getUserByEmail(email);
        Wishlist wishlist = customUser.getWishlist();
        wishlist.getWishlistProducts().add(data);
        wishlistDataService.createOrUpdateWishlist(wishlist);
        return data;
    }


}
