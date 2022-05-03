package com.devcourse.coffeeorder.global.exception.notfound;

public class CategoryNotFoundException extends NotFoundException{
    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String category) {
        super(String.format("can't find a %s!", category));
    }
}
