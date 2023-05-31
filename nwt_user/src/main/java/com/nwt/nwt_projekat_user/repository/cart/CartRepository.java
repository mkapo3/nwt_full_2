package com.nwt.nwt_projekat_user.repository.cart;

import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart getById(Long id);


    @Query("select distinct c from Cart c join c.cartProducts cp on cp.productId = :productId")
    List<Cart> findCartsByProductId(@Param("productId") Long productId);

}
