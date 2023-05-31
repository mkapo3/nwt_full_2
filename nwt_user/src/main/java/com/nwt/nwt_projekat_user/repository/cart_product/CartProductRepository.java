package com.nwt.nwt_projekat_user.repository.cart_product;

import com.nwt.nwt_projekat_user.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    public CartProduct getById(Long id);

    void removeCartProductByProductId(Long productId);

}
