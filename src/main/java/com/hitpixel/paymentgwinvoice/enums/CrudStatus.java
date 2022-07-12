package com.hitpixel.paymentgwinvoice.enums;

public enum CrudStatus {
    CREATED("created"),
    UPDATED("updated"),
    DELETED("deleted"),
    FAILED("failed");

    private final String status;
    CrudStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
}
