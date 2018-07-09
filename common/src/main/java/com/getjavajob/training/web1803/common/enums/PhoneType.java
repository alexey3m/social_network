package com.getjavajob.training.web1803.common.enums;

public enum PhoneType {
    MOBILE(0),
    WORK(1),
    HOME(2);

    private int status;

    PhoneType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}