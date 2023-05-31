package com.spingdatajpa.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.error.ErrorConstants;
import com.spingdatajpa.springboot.error.exception.WrappedException;
import com.spingdatajpa.springboot.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/product/addToCart")
public class CommunicationController {
    @Autowired
    ProductService productService;

    RestTemplate restTemplate;

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public CommunicationController(){
        this.restTemplate = new RestTemplate();
    }
    @PostMapping("/add")
    public Product AddToCart(int cartId, Product p){
        if(p.getState().isEmpty()) throw new WrappedException(ErrorConstants.NOT_FOUND);
        Product product = restTemplate.postForObject("user/cart/"+cartId, p, p.getClass());
        return product;
                ///api/cart
    }
}
