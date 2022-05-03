package com.devcourse.coffeeorder.global.exception.badrequest;

public class OrderException extends BadRequestException {
    public OrderException() {
    }

    public OrderException(String message) {
        super(message);
    }
}
