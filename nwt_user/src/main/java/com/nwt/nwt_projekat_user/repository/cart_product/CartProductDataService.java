package com.nwt.nwt_projekat_user.repository.cart_product;

import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductDataService {

    @Autowired
    CartProductRepository cartProductRepository;

    public CartProduct getCartProductById(Long id){
        return cartProductRepository.getById(id);
    }

    public CartProduct createOrUpdateCartProduct(CartProduct customUser){
        return cartProductRepository.save(customUser);
    }

    public List<CartProduct> findAllCartProductsByCart(Cart wishlist){
        return cartProductRepository.findAllByCart(wishlist);
    }

    public void removeCartProductByProductId(Long productId){
        cartProductRepository.removeCartProductByProductId(productId);
    }

}

