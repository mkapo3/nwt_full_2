package com.springnwt.OrderService.Repository;

import com.springnwt.OrderService.Model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer> {
}
