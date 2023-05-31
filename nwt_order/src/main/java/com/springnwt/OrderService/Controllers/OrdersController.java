package com.springnwt.OrderService.Controllers;

import com.springnwt.OrderService.Model.Orders;
import com.springnwt.OrderService.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;
    @PostMapping("")
    Orders newOrder(@RequestBody Orders order) {
        return orderRepository.save(order);
    }

    @GetMapping("/orders")
    public @ResponseBody Iterable<Orders> getAllOrders(){
        return orderRepository.findAll();
    }
}
