package com.example.nwt_api_gateway.error;

import com.example.nwt_api_gateway.error.exception.WrappedException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class ReactiveExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
    private final HttpStatus defaultStatus;

    public ReactiveExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                    ApplicationContext applicationContext, Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode,
                                    HttpStatus defaultStatus) {
        super(errorAttributes, resources, applicationContext);
        this.exceptionToStatusCode = exceptionToStatusCode;
        this.defaultStatus = defaultStatus;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);
        HttpStatus httpStatus;
        if (error instanceof Exception exception) {
            httpStatus = exceptionToStatusCode.getOrDefault(exception.getClass(), defaultStatus);
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(httpStatus.value());
        errorResponse.setMessage(error.getMessage());
        errorResponse.setErrors(new HashMap<>());
        errorResponse.setStatus(httpStatus.value());
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorResponse)
                );
    }

    private ErrorResponse createErrorResponse(ErrorConstants errorConstant){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorConstant.getCode());
        errorResponse.setMessage(errorConstant.getMessage());
        errorResponse.setStatus(errorConstant.getStatus().value());
        errorResponse.setErrors(new HashMap<>());

        return errorResponse;
    }
}