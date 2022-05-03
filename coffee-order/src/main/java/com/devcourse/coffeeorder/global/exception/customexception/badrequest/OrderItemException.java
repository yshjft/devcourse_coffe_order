package com.devcourse.coffeeorder.global.exception.customexception.badrequest;

public class OrderItemException extends BadRequestException {
    public OrderItemException() {
    }

    public OrderItemException(String message) {
        super(message);
    }
}
