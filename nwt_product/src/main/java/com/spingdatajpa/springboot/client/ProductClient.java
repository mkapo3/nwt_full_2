package com.spingdatajpa.springboot.client;

import com.spingdatajpa.springboot.service.ConfigurationManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import proto_generated.AddProductRequest;
import proto_generated.ProductServiceGrpc;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class ProductClient {

    private ProductServiceGrpc.ProductServiceBlockingStub stub;

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
        stub = ProductServiceGrpc.newBlockingStub(channel);
    }

    @Async
    public void addProduct(Integer productId, LocalDateTime createdDateTime){
        stub.addProduct(AddProductRequest.newBuilder()
                .setTimestamp(createdDateTime.toString())
                .setProductId(productId)
                .setServiceName(configurationManager.getSpringApplicationName())
                .build());
    }


}
