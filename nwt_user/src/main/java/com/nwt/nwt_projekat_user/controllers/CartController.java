package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CartProduct;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.repository.cart.CartDataService;
import com.nwt.nwt_projekat_user.repository.cart_product.CartProductDataService;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.request_response.cart.CartResponse;
import com.nwt.nwt_projekat_user.services.ProductService;
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
    CartProductDataService cartProductDataService;

    @Autowired
    ProductService productService;

    @GetMapping("/admin/{id}")
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

    @PostMapping("/admin/{id}")
    @ResponseBody
    public CartProduct addCartProduct(HttpServletRequest request, @PathVariable Long id, @RequestBody CartProduct data){
        productService.getProduct(data.getProductId(), request.getHeader("Authorization"));
        Cart cart = cartDataService.findCartById(id);
        if (cart == null){
            throw new WrappedException(NOT_FOUND);
        }
        data.setCart(cart);
        return cartProductDataService.createOrUpdateCartProduct(data);
    }

    @GetMapping("/{email}")
    @ResponseBody
    public Cart getCurrentUserCart(@PathVariable String email){
        CustomUser customUser = customUserDataService.getUserByEmail(email);
        return customUser.getCart();
    }

    @PostMapping("/{email}")
    @ResponseBody
    public CartProduct addCurrentUserCartProduct(@PathVariable String email, @RequestBody CartProduct data){
        Cart cart = customUserDataService.getUserByEmail(email).getCart();
        cart.getCartProducts().add(data);
        cartDataService.createOrUpdateCart(cart);
        return data;
    }

}
