package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.models.Wishlist;
import com.nwt.nwt_projekat_user.models.WishlistProduct;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.repository.wishlist.WishlistDataService;
import com.nwt.nwt_projekat_user.repository.wishlist_product.WishlistProductDataService;
import com.nwt.nwt_projekat_user.request_response.GeneralSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/user/wishlist")
public class WishlistController {

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    WishlistDataService wishlistDataService;

    @Autowired
    WishlistProductDataService wishlistProductDataService;

    @GetMapping("{email}")
    @ResponseBody
    public Wishlist getCurrentUserWishlist(@PathVariable String email){
        CustomUser customUser = customUserDataService.getUserByEmail(email);
        return customUser.getWishlist();
    }

    @PostMapping("/{email}")
    @ResponseBody
    public WishlistProduct addWishlistProduct(@PathVariable String email, @RequestBody WishlistProduct data){
        CustomUser customUser = customUserDataService.getUserByEmail(email);
        Wishlist wishlist = customUser.getWishlist();
        data.setWishlist(wishlist);
        wishlistProductDataService.createOrUpdateWishlistProduct(data);
        return data;
    }

    @DeleteMapping("/wishlist-product/{id}")
    @ResponseBody
    public GeneralSuccessResponse removeWishlistProduct(@PathVariable Long id){
        wishlistProductDataService.removeWishlistProduct(id);
        return new GeneralSuccessResponse();
    }

    @GetMapping("/wishlist-products/{email}")
    @ResponseBody
    public List<WishlistProduct> getAllUserWishlistProducts(@PathVariable String email, HttpServletRequest request){
        CustomUser user = customUserDataService.getUserByEmail(email);
        if(user == null){
            throw new WrappedException(NOT_FOUND);
        }
        return wishlistProductDataService.findAllWishlistProductsByWishlist(user.getWishlist());
    }

}
