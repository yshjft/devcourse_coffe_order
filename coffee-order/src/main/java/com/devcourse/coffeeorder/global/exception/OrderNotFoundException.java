package com.devcourse.coffeeorder.global.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String orderId) {
        super(String.format("can't find a order(%s)!", orderId));
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }

    protected OrderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
