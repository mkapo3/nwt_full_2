package com.nwt.nwt_projekat_user.rmq;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nwt.nwt_projekat_user.repository.cart.CartDataService;
import com.nwt.nwt_projekat_user.repository.cart_product.CartProductDataService;
import com.nwt.nwt_projekat_user.repository.wishlist_product.WishlistProductDataService;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProductReceiver {

    private final CartDataService cartDataService;

    private final CartProductDataService cartProductDataService;

    private final WishlistProductDataService wishlistProductDataService;

    public ProductReceiver(CartDataService cartDataService, CartProductDataService cartProductDataService, WishlistProductDataService wishlistProductDataService) {
        this.cartDataService = cartDataService;
        this.cartProductDataService = cartProductDataService;
        this.wishlistProductDataService = wishlistProductDataService;
    }

    @RabbitListener(queues = {"q.product-changes"})
    @Transactional
    public void deleteProductFromCart(ObjectNode data){
        cartProductDataService.removeCartProductByProductId(data.get("deletedProductId").asLong());
        wishlistProductDataService.removeWishlistProductByProductId(data.get("deletedProductId").asLong());
    }
}
