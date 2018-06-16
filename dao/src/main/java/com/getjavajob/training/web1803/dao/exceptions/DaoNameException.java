package com.getjavajob.training.web1803.dao.exceptions;

public class DaoNameException extends Exception {
    public DaoNameException() {
    }

    public DaoNameException(String message) {
        super(message);
    }

    public DaoNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoNameException(Throwable cause) {
        super(cause);
    }

    public DaoNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
