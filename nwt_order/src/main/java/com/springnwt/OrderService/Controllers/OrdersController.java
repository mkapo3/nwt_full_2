package com.springnwt.OrderService.Controllers;

import com.springnwt.OrderService.Model.Orders;
import com.springnwt.OrderService.Repository.OrderRepository;
import com.springnwt.OrderService.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("")
    Orders newOrder(@RequestBody Orders order) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Orders newOrder = orderRepository.save(order);
        orderClient.addOrder(newOrder.getId(), localDateTime);
        return newOrder;
    }

    @GetMapping("/orders")
    public @ResponseBody Iterable<Orders> getAllOrders(){
        return orderRepository.findAll();
    }
}
