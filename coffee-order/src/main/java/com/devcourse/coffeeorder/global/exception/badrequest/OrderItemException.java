package com.devcourse.coffeeorder.global.exception.badrequest;

public class OrderItemException extends BadRequestException {
    public OrderItemException() {
    }

    public OrderItemException(String message) {
        super(message);
    }
}
