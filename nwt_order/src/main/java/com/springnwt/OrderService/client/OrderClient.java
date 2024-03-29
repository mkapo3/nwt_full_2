package com.springnwt.OrderService.client;

import com.springnwt.OrderService.Service.ConfigurationManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import proto_generated.AddOrderRequest;
import proto_generated.OrderServiceGrpc;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class OrderClient {

    private OrderServiceGrpc.OrderServiceBlockingStub stub;

    @Autowired
    ConfigurationManager configurationManager;

    @Autowired
    DiscoveryClient discoveryClient;

    @PostConstruct
    public void init (){
        String host = discoveryClient.getInstances("NWTPROJEKATUSER").get(0).getHost();
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, 8180)
                .usePlaintext()
                .build();
        stub = OrderServiceGrpc.newBlockingStub(channel);
    }

    @Async
    public void addOrder(Long orderId, LocalDateTime createdDateTime){
        stub.addOrder(AddOrderRequest.newBuilder()
                .setTimestamp(createdDateTime.toString())
                .setOrderId(orderId)
                .setServiceName(configurationManager.getSpringApplicationName())
                .build());
    }


}
