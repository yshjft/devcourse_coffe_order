package com.devcourse.coffeeorder.global.exception.notfound;

public class OrderItemNotFoundException extends NotFoundException{
    public OrderItemNotFoundException() {
        super();
    }

    public OrderItemNotFoundException(String orderItemId) {
        super(String.format("can't find a orderItem(%s)!", orderItemId));
    }
}
