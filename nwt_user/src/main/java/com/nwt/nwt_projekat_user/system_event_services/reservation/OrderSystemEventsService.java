package com.nwt.nwt_projekat_user.system_event_services.reservation;

import com.google.protobuf.Descriptors;
import com.nwt.nwt_projekat_user.models.SystemEventsLog;
import com.nwt.nwt_projekat_user.repository.system_events_log.SystemEventsLogService;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import proto_generated.AddOrderRequest;
import proto_generated.AddOrderResponse;
import proto_generated.OrderServiceGrpc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
public class OrderSystemEventsService extends OrderServiceGrpc.OrderServiceImplBase {

    private static final Log logger = LogFactory.getLog(OrderSystemEventsService.class);

    final
    SystemEventsLogService systemEventsLogService;

    public OrderSystemEventsService(SystemEventsLogService systemEventsLogService) {
        this.systemEventsLogService = systemEventsLogService;
    }

    @Override
    public void addOrder(AddOrderRequest request, StreamObserver<AddOrderResponse> responseObserver) {
        String message = new StringBuilder()
                .append("Request received")
                .toString();

        AddOrderResponse response = AddOrderResponse.newBuilder()
                .setMessageReceived(message)
                .build();

        String fullMessage = logRequest(request.getAllFields().entrySet());

        SystemEventsLog systemEventsLog = new SystemEventsLog();
        systemEventsLog.setServiceName(request.getServiceName());
        systemEventsLog.setTimestamp(LocalDateTime.parse(request.getTimestamp()));
        systemEventsLog.setMessage("Order added: " + request.getOrderId());
        systemEventsLogService.createOrUpdate(systemEventsLog);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    private String logRequest(Set<Map.Entry<Descriptors.FieldDescriptor, Object>> entries){
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("------------------------------\n");
        stringBuilder.append("New order added: \n");
        for (Map.Entry<Descriptors.FieldDescriptor, Object> field : entries){
            stringBuilder.append(field.getKey().toString() + ": " + field.getValue() + "\n");
        }
        stringBuilder.append("------------------------------\n");
        stringBuilder.append("------------------------------\n");

        logger.info(stringBuilder.toString());

        return stringBuilder.toString();
    }
}
