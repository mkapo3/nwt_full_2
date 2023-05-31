package com.springnwt.OrderService.error.exception;


import com.springnwt.OrderService.error.ErrorConstants;

public class WrappedException extends RuntimeException{

    private ErrorConstants errorConstant;

    public WrappedException(ErrorConstants errorConstant) {
        super(errorConstant.getMessage());
        this.errorConstant = errorConstant;
    }

    public ErrorConstants getErrorConstant() {
        return errorConstant;
    }

    public void setErrorConstant(ErrorConstants errorConstant) {
        this.errorConstant = errorConstant;
    }
}