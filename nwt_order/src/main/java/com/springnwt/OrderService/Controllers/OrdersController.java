package com.springnwt.OrderService.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springnwt.OrderService.Model.Orders;
import com.springnwt.OrderService.Repository.OrderRepository;
import com.springnwt.OrderService.client.OrderClient;
import com.springnwt.OrderService.error.exception.WrappedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.springnwt.OrderService.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @PostMapping("")
    Orders newOrder(HttpServletRequest request, @RequestBody Orders order) {
        String host = discoveryClient.getInstances("NWTPROJEKATUSER").get(0).getUri().toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", request.getHeader("Authorization"));
        HttpEntity<ObjectNode> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(host + "/user/custom-user/admin/" + order.getUserId(), HttpMethod.GET, entity, String.class);
        if (response.getBody() == null){
            throw new WrappedException(NOT_FOUND);
        }
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
