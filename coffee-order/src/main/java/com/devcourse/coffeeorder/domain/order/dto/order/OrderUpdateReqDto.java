package com.devcourse.coffeeorder.domain.order.dto.order;

import com.devcourse.coffeeorder.global.exception.badrequest.WrongInputException;
import org.springframework.util.StringUtils;

public class OrderUpdateReqDto {
    private String address;
    private String postcode;

    public OrderUpdateReqDto(String address, String postcode) {
        validateParams(address, postcode);
        this.address = address;
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    private void validateParams(String address, String postcode) {
        if(!StringUtils.hasText(address)) {
            throw new WrongInputException("address is required property!");
        }
        if(!StringUtils.hasText(postcode)) {
            throw new WrongInputException("postcode is required property!");
        }
    }
}
