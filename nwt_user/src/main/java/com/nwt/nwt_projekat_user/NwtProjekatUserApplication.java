package com.nwt.nwt_projekat_user;

import com.nwt.nwt_projekat_user.repository.system_events_log.SystemEventsLogService;
import com.nwt.nwt_projekat_user.system_event_services.reservation.OrderSystemEventsService;
import com.nwt.nwt_projekat_user.system_event_services.reservation.ProductSystemEventsService;
import com.nwt.nwt_projekat_user.system_event_services.reservation.ReservationSystemEventsService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class NwtProjekatUserApplication {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(NwtProjekatUserApplication.class, args);

        Server server = ServerBuilder
                .forPort(8180)
                .addService(new ReservationSystemEventsService(applicationContext.getBean(SystemEventsLogService.class)))
                .addService(new ProductSystemEventsService(applicationContext.getBean(SystemEventsLogService.class)))
                .addService(new OrderSystemEventsService(applicationContext.getBean(SystemEventsLogService.class))).build();
        try {
            server.start();
            server.awaitTermination();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
