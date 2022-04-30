package com.devcourse.coffeeorder.global.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String productId) {
        super(String.format("can't find a product(%s)!", productId));
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProductNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
