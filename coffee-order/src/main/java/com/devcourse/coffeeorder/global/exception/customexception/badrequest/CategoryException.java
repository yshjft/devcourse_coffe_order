package com.devcourse.coffeeorder.global.exception.customexception.badrequest;

public class CategoryException extends BadRequestException{
    public CategoryException() {
        super();
    }

    public CategoryException(String message) {
        super(message);
    }
}
