package com.nwt.nwt_projekat_user.request_response.reservation;

public class ReservationReq {

    private long productId;

    private long userId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
