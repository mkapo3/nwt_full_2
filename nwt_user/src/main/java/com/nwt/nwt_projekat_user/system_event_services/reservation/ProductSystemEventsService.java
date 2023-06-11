package com.nwt.nwt_projekat_user.system_event_services.reservation;

import com.google.protobuf.Descriptors;
import com.nwt.nwt_projekat_user.models.SystemEventsLog;
import com.nwt.nwt_projekat_user.repository.system_events_log.SystemEventsLogService;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import proto_generated.AddProductRequest;
import proto_generated.AddProductResponse;
import proto_generated.ProductServiceGrpc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
public class ProductSystemEventsService extends ProductServiceGrpc.ProductServiceImplBase {

    private static final Log logger = LogFactory.getLog(ProductSystemEventsService.class);

    final
    SystemEventsLogService systemEventsLogService;

    public ProductSystemEventsService(SystemEventsLogService systemEventsLogService) {
        this.systemEventsLogService = systemEventsLogService;
    }

    @Override
    public void addProduct(AddProductRequest request, StreamObserver<AddProductResponse> responseObserver) {
        String message = new StringBuilder()
                .append("Request received")
                .toString();

        AddProductResponse response = AddProductResponse.newBuilder()
                .setMessageReceived(message)
                .build();

        String fullMessage = logRequest(request.getAllFields().entrySet());

        SystemEventsLog systemEventsLog = new SystemEventsLog();
        systemEventsLog.setServiceName(request.getServiceName());
        systemEventsLog.setTimestamp(LocalDateTime.parse(request.getTimestamp()));
        systemEventsLog.setMessage("Product added: " + request.getProductId());
        systemEventsLogService.createOrUpdate(systemEventsLog);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    private String logRequest(Set<Map.Entry<Descriptors.FieldDescriptor, Object>> entries){
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("------------------------------\n");
        stringBuilder.append("New product added: \n");
        for (Map.Entry<Descriptors.FieldDescriptor, Object> field : entries){
            stringBuilder.append(field.getKey().toString() + ": " + field.getValue() + "\n");
        }
        stringBuilder.append("------------------------------\n");
        stringBuilder.append("------------------------------\n");

        logger.info(stringBuilder.toString());

        return stringBuilder.toString();
    }
}
