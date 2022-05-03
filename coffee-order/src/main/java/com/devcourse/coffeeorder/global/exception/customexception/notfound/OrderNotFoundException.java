package com.devcourse.coffeeorder.global.exception.customexception.notfound;

public class OrderNotFoundException extends NotFoundException{
    public OrderNotFoundException() {
        super();
    }
    public OrderNotFoundException(String orderId) {
        super(String.format("can't find a order(%s)!", orderId));
    }
}

