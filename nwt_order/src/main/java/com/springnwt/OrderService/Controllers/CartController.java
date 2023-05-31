package com.springnwt.OrderService.Controllers;

import com.springnwt.OrderService.Model.Cart;
import com.springnwt.OrderService.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/cart")
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("")
    public @ResponseBody Iterable<Cart> getAllProducts(){
        return cartRepository.findAll();
    }

}
