package com.myfirstandroidjava.salesapp.models;

public class UpdateOrderRequest {
    private String newStatus;

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public UpdateOrderRequest(String newStatus) {
        this.newStatus = newStatus;
    }
}
