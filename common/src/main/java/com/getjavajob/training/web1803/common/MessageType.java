package com.getjavajob.training.web1803.common;

public enum MessageType {
    ACCOUNT(0),
    ACCOUNT_WALL(1),
    GROUP_WALL(2);

    private int status;

    MessageType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}