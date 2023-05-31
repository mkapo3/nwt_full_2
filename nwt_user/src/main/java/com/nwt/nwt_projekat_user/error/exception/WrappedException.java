package com.nwt.nwt_projekat_user.error.exception;

import com.nwt.nwt_projekat_user.error.ErrorConstants;

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
