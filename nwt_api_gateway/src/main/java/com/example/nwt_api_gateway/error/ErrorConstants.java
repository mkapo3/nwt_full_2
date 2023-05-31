package com.example.nwt_api_gateway.error;

import org.springframework.http.HttpStatus;

public enum ErrorConstants
{
    GENERAL_ERROR("General error", 10001, HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENTS("Invalid arguments", 10002, HttpStatus.BAD_REQUEST),
    NOT_FOUND("Not found", 10003, HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("User with given credentials already exists", 10004, HttpStatus.BAD_REQUEST),

    INVALID_LOGIN_DETAILS("Invalid login details", 10005, HttpStatus.FORBIDDEN),
    TOKEN_NOT_FOUND("Token not found", 10006, HttpStatus.FORBIDDEN);

    private String message;

    private Integer code;

    private HttpStatus status;

    ErrorConstants(String message, int code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
