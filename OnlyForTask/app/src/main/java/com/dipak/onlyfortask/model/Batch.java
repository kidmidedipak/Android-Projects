package com.dipak.onlyfortask.model;

public class Batch {

    String batchNo;
    int quantity;

    public Batch(String batchNo, int quantity) {
        this.batchNo = batchNo;
        this.quantity = quantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public int getQuantity() {
        return quantity;
    }
}
