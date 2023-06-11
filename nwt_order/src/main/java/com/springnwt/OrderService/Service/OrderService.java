package com.springnwt.OrderService.Service;

import com.springnwt.OrderService.Model.Orders;
import com.springnwt.OrderService.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Orders createOrUpdate(Orders orders){
        return orderRepository.save(orders);
    }

    public List<Orders> findAll(){
        return orderRepository.findAll();
    }

}
