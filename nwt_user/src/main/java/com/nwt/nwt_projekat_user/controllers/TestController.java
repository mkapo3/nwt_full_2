package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.external_responses.CategoryResponse;
import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.models.Order;
import com.nwt.nwt_projekat_user.repository.cart.CartDataService;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/test")
public class TestController {

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    CartDataService cartDataService;

    @Autowired
    ProductService productService;

    @GetMapping("/test-external-service")
    @ResponseBody
    public CategoryResponse testNedinaService(HttpServletRequest request){
        return productService.getCategory(1L, request.getHeader("Authorization"));
    }

    @PostMapping("")
    @ResponseBody
    public CustomUser insertUser(){

        CustomUser customUser = new CustomUser();
        customUser.setAddress("address");
        customUser.setEmail("mkapo3@etf.unsa.ba");
        customUser.setName("muharem");
        customUser.setPhoneNumber("+387112233444");

        customUserDataService.createOrUpdateUser(customUser);

        return customUser;
    }

    @PostMapping("/order")
    @ResponseBody
    public Order insertCartAndOrder(){

        Order order = new Order();
        order.setAddress("address");

        Cart cart = new Cart();
        cart.setOrder(order);
        cart.setDeleted(false);

        cartDataService.createOrUpdateCart(cart);

        return order;
    }

    @GetMapping("/ping")
    @ResponseBody
    public String ping(){
        return "ping";
    }


}

