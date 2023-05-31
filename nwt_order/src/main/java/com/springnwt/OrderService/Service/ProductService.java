package com.springnwt.OrderService.Service;

import com.springnwt.OrderService.Model.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public abstract Product addProduct(Product product);
}
