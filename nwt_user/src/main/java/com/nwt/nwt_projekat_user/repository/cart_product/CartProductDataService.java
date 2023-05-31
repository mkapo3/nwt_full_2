package com.nwt.nwt_projekat_user.repository.cart_product;

import com.nwt.nwt_projekat_user.models.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void removeCartProductByProductId(Long productId){
        cartProductRepository.removeCartProductByProductId(productId);
    }

}

