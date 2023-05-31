package com.nwt.nwt_projekat_user.repository.order;

import com.nwt.nwt_projekat_user.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDataService {

    @Autowired
    OrderRepository orderRepository;

    public Order getOrderById(Long id){
        return orderRepository.getById(id);
    }

    public void createOrUpdateOrder(Order customUser){
        orderRepository.save(customUser);
    }

}

