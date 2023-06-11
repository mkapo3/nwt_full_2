package com.nwt.nwt_projekat_user.repository.wishlist_product;

import com.nwt.nwt_projekat_user.models.Wishlist;
import com.nwt.nwt_projekat_user.models.WishlistProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistProductDataService {

    @Autowired
    WishlistProductRepository wishlistProductRepository;

    public WishlistProduct getWishlistProductById(Long id){
        Optional<WishlistProduct> wishlistProduct = wishlistProductRepository.findById(id);
        return wishlistProduct.orElse(null);
    }

    public WishlistProduct createOrUpdateWishlistProduct(WishlistProduct customUser){
        return wishlistProductRepository.save(customUser);
    }

    public List<WishlistProduct> findAllWishlistProductsByWishlist(Wishlist wishlist){
        return wishlistProductRepository.findAllByWishlist(wishlist);
    }
    public void removeWishlistProductByProductId(Long productId){
        wishlistProductRepository.removeWishlistProductByProductId(productId);
    }

}

