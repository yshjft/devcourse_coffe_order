package com.devcourse.coffeeorder.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.orderitem.dto.OrderItemWithProductDetailResDto;
import com.devcourse.coffeeorder.domain.order.entity.OrderStatus;

public class OrderDetailResDto {
    private UUID orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private List<OrderItemWithProductDetailResDto> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderDetailResDto(UUID orderId, String email, String address, String postcode, OrderStatus orderStatus, List<OrderItemWithProductDetailResDto> orderItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderDetailResDtoBuilder builder() {
        return new OrderDetailResDtoBuilder();
    }

    public static class OrderDetailResDtoBuilder {
        private UUID orderId;
        private String email;
        private String address;
        private String postcode;
        private OrderStatus orderStatus;
        private List<OrderItemWithProductDetailResDto> orderItems;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public OrderDetailResDtoBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderDetailResDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public OrderDetailResDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public OrderDetailResDtoBuilder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public OrderDetailResDtoBuilder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderDetailResDtoBuilder orderItems(List<OrderItemWithProductDetailResDto> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public OrderDetailResDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderDetailResDtoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public OrderDetailResDto build() {
            return new OrderDetailResDto(orderId, email, address, postcode, orderStatus, orderItems, createdAt, updatedAt);
        }
    }

    public UUID getOrderId() {
        return orderId;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItemWithProductDetailResDto> getOrderItems() {
        return orderItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
