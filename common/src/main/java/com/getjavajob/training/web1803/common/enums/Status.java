package com.getjavajob.training.web1803.common.enums;

public enum Status {
    UNKNOWN(-1),
    PENDING(0),
    ACCEPTED(1),
    DECLINE(2);

    private int status;

    Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}