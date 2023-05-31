package com.springnwt.OrderService.Repository;

import com.springnwt.OrderService.Model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    private void addToCart(){

    }
}
