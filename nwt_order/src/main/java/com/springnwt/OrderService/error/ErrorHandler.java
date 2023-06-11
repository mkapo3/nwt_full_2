package com.springnwt.OrderService.error;

import com.springnwt.OrderService.error.exception.WrappedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @ExceptionHandler({WrappedException.class})
    protected ResponseEntity<ErrorResponse> handleWrappedException(WrappedException ex, WebRequest request){
        ErrorConstants errorConstant = ex.getErrorConstant();

        ErrorResponse errorResponse = createErrorResponse(errorConstant);

        return new ResponseEntity(errorResponse, errorConstant.getStatus());
    }

    @ExceptionHandler({HttpClientErrorException.class})
    protected ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request){
        ErrorResponse errorResponse = ex.getResponseBodyAs(ErrorResponse.class);
        return new ResponseEntity(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        ErrorConstants errorConstant = ErrorConstants.INVALID_ARGUMENTS;

        ErrorResponse errorResponse = createErrorResponse(errorConstant);
        logger.info("not_valid_argument: " + errorResponse.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        errorResponse.setErrors(errors);
        return new ResponseEntity(errorResponse, errorConstant.getStatus());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(Exception ex, WebRequest request){

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(10000);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(400);
        errorResponse.setErrors(new HashMap<>());

        return new ResponseEntity(errorResponse, HttpStatusCode.valueOf(400));
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
