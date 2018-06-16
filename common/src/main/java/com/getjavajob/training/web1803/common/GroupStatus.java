package com.getjavajob.training.web1803.common;

public enum GroupStatus {
    UNKNOWN(0),
    PENDING(1),
    ACCEPTED(2),
    DECLINE(3);

    private int status;

    GroupStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}