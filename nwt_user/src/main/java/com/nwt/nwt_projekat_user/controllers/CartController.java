package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CartProduct;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.repository.cart.CartDataService;
import com.nwt.nwt_projekat_user.repository.cart_product.CartProductDataService;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.request_response.GeneralSuccessResponse;
import com.nwt.nwt_projekat_user.request_response.cart.CartResponse;
import com.nwt.nwt_projekat_user.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/cart-product/{id}")
    @ResponseBody
    public GeneralSuccessResponse removeCartProduct(@PathVariable Long id){
        cartProductDataService.removeCartProduct(id);
        return new GeneralSuccessResponse();
    }

    @PostMapping("/{email}")
    @ResponseBody
    public CartProduct addCurrentUserCartProduct(@PathVariable String email, @RequestBody CartProduct data){
        Cart cart = customUserDataService.getUserByEmail(email).getCart();
        data.setCart(cart);
        cartProductDataService.createOrUpdateCartProduct(data);
        return data;
    }

    @GetMapping("/cart-products/{email}")
    @ResponseBody
    public List<CartProduct> getAllUserCartProducts(@PathVariable String email, HttpServletRequest request){
        CustomUser user = customUserDataService.getUserByEmail(email);
        if(user == null){
            throw new WrappedException(NOT_FOUND);
        }
        return cartProductDataService.findAllCartProductsByCart(user.getCart());
    }

}
