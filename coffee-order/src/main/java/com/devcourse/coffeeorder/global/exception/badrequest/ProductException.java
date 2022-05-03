package com.devcourse.coffeeorder.global.exception.badrequest;

public class ProductException extends BadRequestException{
    public ProductException() {
        super();
    }

    public ProductException(String message) {
        super(message);
    }
}
