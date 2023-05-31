package com.nwt.nwt_projekat_user.clients;

import com.nwt.nwt_projekat_user.services.ConfigurationManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import proto_generated.AddReservationRequest;
import proto_generated.ReservationServiceGrpc;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservationClient {

    private ReservationServiceGrpc.ReservationServiceBlockingStub stub;

    @Autowired
    ConfigurationManager configurationManager;

    @PostConstruct
    public void init (){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8180)
                .usePlaintext()
                .build();
        stub = ReservationServiceGrpc.newBlockingStub(channel);
    }

    @Async
    public void addReservation(Long userId, LocalDateTime createdDateTime, Long productId){
        stub.addReservation(AddReservationRequest.newBuilder()
                .setUserId(userId)
                .setTimestamp(createdDateTime.toString())
                .setProductId(productId)
                .setServiceName(configurationManager.getSpringApplicationName())
                .build());
    }


}
