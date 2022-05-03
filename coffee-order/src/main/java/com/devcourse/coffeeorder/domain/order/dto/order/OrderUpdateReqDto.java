package com.devcourse.coffeeorder.domain.order.dto.order;

import com.devcourse.coffeeorder.global.exception.customexception.badrequest.WrongInputException;
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
        if(!StringUtils.hasText(address) || address.length() > 200) {
            throw new WrongInputException("address must not be blank! (1 <= address <= 200)");
        }
        if(!StringUtils.hasText(postcode) || postcode.length() > 200) {
            throw new WrongInputException("postcode must not be blank! (1 <= postcode <= 200)");
        }
    }
}
