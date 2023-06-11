package com.nwt.nwt_projekat_user.repository.cart;

import com.nwt.nwt_projekat_user.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDataService {

    @Autowired
    CartRepository cartRepository;

    public Cart findCartById(Long id){
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElse(null);
    }

    public List<Cart> findCartsByProductId(Long productId){
        return cartRepository.findCartsByProductId(productId);
    }

    public void createOrUpdateCart(Cart customUser){
        cartRepository.save(customUser);
    }

    public void remove(Cart cart) {
        cartRepository.delete(cart);
    }
}

