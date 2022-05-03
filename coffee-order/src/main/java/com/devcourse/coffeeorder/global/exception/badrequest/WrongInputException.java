package com.devcourse.coffeeorder.global.exception.badrequest;

public class WrongInputException extends BadRequestException{
    public WrongInputException() {
        super();
    }

    public WrongInputException(String message) {
        super(message);
    }
}
