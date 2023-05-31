package com.springnwt.OrderService.Controllers;

import com.springnwt.OrderService.Model.Product;
import com.springnwt.OrderService.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/product")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("")
    Product newProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/products")
    public @ResponseBody Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

}
