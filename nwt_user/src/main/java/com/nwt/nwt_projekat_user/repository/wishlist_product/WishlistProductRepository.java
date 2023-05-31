package com.nwt.nwt_projekat_user.repository.wishlist_product;

import com.nwt.nwt_projekat_user.models.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {

    @Override
    Optional<WishlistProduct> findById(Long id);

    void removeWishlistProductByProductId(Long productId);

}
