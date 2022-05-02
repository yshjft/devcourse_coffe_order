package com.devcourse.coffeeorder.domain.orderItem.dto;

import com.devcourse.coffeeorder.global.exception.WrongInputException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItemUpdateReqDto {
    private int quantity;

    @JsonCreator
    public OrderItemUpdateReqDto(int quantity) {
        validateParam(quantity);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validateParam(int quantity) {
        if(quantity <= 0) {
            throw new WrongInputException("quantity must bigger than 0");
        }
    }
}
