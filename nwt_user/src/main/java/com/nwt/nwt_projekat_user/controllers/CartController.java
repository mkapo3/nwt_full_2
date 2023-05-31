package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CartProduct;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.repository.cart.CartDataService;
import com.nwt.nwt_projekat_user.repository.cart_product.CartProductDataService;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.request_response.cart.CartResponse;
import com.nwt.nwt_projekat_user.services.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    CartDataService cartDataService;

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    CartProductDataService cartProductDataService;

    @GetMapping("/{id}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public CartResponse getCart(@PathVariable Long id, HttpServletRequest request){
        Cart cart = cartDataService.findCartById(id);
        if (cart == null){
            throw new WrappedException(NOT_FOUND);
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setDeleted(cart.getDeleted());
        return cartResponse;
    }

    @PostMapping("/{id}")
    @ResponseBody
    public CartProduct addCartProduct(@PathVariable Long id, @RequestBody CartProduct data){
        Cart cart = cartDataService.findCartById(id);
        if (cart == null){
            throw new WrappedException(NOT_FOUND);
        }
        data.setCart(cart);
        return cartProductDataService.createOrUpdateCartProduct(data);
    }

    @GetMapping("")
    @ResponseBody
    public Cart getCurrentUserCart(){
        CustomUser customUser = customUserDataService.getUserByEmail(currentUserService.getEmail());
        return customUser.getCart();
    }

    @PostMapping("")
    @ResponseBody
    public CartProduct addCurrentUserCartProduct(@RequestBody CartProduct data){
        Cart cart = customUserDataService.getUserByEmail(currentUserService.getEmail()).getCart();
        cart.getCartProducts().add(data);
        cartDataService.createOrUpdateCart(cart);
        return data;
    }

}
