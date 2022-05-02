package com.devcourse.coffeeorder.global.exception.notfound;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String productId) {
        super(String.format("can't find a product(%s)!", productId));
    }
}


