package com.devcourse.coffeeorder.domain.order.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemCreateReqDto;
import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.WrongInputException;
import org.springframework.util.StringUtils;

public class OrderCreateReqDto {
    private String email;
    private String address;
    private String postcode;
    private List<OrderItemCreateReqDto> orderItems;

    public OrderCreateReqDto(String email, String address, String postcode, List<OrderItemCreateReqDto> orderItems) {
        validateParams(email, address, postcode, orderItems);
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
    }

    public static class OrderCreateReqDtoBuilder {
        private String email;
        private String address;
        private String postcode;
        private List<OrderItemCreateReqDto> orderItems;

        OrderCreateReqDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        OrderCreateReqDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        OrderCreateReqDtoBuilder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        OrderCreateReqDtoBuilder orderItems(List<OrderItemCreateReqDto> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        OrderCreateReqDto build() {
            return new OrderCreateReqDto(email, address, postcode, orderItems);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<OrderItemCreateReqDto> getOrderItems() {
        return orderItems;
    }

    public Order toEntity() {
        return Order.builder()
                .orderId(UUID.randomUUID())
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(OrderStatus.ORDER_ACCEPTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private void validateParams(String email, String address, String postcode, List<OrderItemCreateReqDto> orderItems) {
        if(!StringUtils.hasText(email)) {
            throw new WrongInputException("email is required property!");
        }
        if(!StringUtils.hasText(address)) {
            throw new WrongInputException("address is required property!");
        }
        if(!StringUtils.hasText(postcode)) {
            throw new WrongInputException("postcode is required property!");
        }
        if(orderItems == null || orderItems.size() == 0) {
            throw new WrongInputException("orderItems must have at least 1 element");
        }
    }
}
