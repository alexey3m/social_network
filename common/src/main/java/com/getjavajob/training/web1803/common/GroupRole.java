package com.getjavajob.training.web1803.common;

public enum GroupRole {
    UNKNOWN(0),
    USER(1),
    ADMIN(2);

    private int status;

    GroupRole(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}