package com.devcourse.coffeeorder.global.exception.customexception.badrequest;

public class OrderException extends BadRequestException {
    public OrderException() {
    }

    public OrderException(String message) {
        super(message);
    }
}
