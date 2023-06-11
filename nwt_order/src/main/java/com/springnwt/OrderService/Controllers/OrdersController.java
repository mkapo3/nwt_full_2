package com.springnwt.OrderService.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springnwt.OrderService.Model.Orders;
import com.springnwt.OrderService.Repository.OrderRepository;
import com.springnwt.OrderService.Service.EmailService;
import com.springnwt.OrderService.Service.OrderService;
import com.springnwt.OrderService.client.OrderClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private EmailService emailService;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @PostMapping("")
    @Transactional
    public Orders newOrder(HttpServletRequest request, @RequestBody Orders order) {
        String host = discoveryClient.getInstances("NWTPROJEKATUSER").get(0).getUri().toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", request.getHeader("Authorization"));
        HttpEntity<ObjectNode> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(host + "/user/custom-user/admin/" + order.getUserId(), HttpMethod.GET, entity, String.class);
        ResponseEntity<String> responseCart = restTemplate.exchange(host + "/user/cart/admin/" + order.getCartId(), HttpMethod.GET, entity, String.class);
        try {
            ObjectNode responseBody = OBJECT_MAPPER.readValue(response.getBody(), ObjectNode.class);
            ObjectNode responseCartBody = OBJECT_MAPPER.readValue(responseCart.getBody(), ObjectNode.class);
            emailService.sendEmail(responseBody.get("email").asText(),
                    "Order added",
                    "Your order has been added.\nYour cart's id is: " + responseCartBody.get("id"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Orders newOrder = orderService.createOrUpdate(order);
        orderClient.addOrder(newOrder.getId(), localDateTime);
        restTemplate.exchange(host + "/user/cart/create/" + order.getUserId(), HttpMethod.POST, entity, String.class);

        return newOrder;
    }

    @GetMapping("/orders")
    public @ResponseBody List<Orders> getAllOrders(){
        return orderService.findAll();
    }
}
