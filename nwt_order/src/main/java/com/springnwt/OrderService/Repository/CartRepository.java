package com.springnwt.OrderService.Repository;

import com.springnwt.OrderService.Model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {
}
