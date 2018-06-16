package com.getjavajob.training.web1803.common;

public enum PhoneType {
    HOME(0),
    WORK(1),
    ADDITIONAL(2);

    private int status;

    PhoneType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}