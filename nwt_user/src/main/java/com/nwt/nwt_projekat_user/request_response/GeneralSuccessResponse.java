package com.nwt.nwt_projekat_user.request_response;

public class GeneralSuccessResponse {

    private String message = "success";
    private int status = 200;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
