package com.devcourse.coffeeorder.global.exception.customexception;

public class CreationException extends RuntimeException{

    public CreationException() {
        super();
    }

    public CreationException(String message) {
        super(message);
    }

    public CreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationException(Throwable cause) {
        super(cause);
    }

    protected CreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
