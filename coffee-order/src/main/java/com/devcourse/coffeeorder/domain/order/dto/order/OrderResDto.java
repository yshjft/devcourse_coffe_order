package com.devcourse.coffeeorder.domain.order.dto.order;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;

public class OrderResDto {
    private UUID orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderResDto(UUID orderId, String email, String address, String postcode, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderResDtoBuilder builder() {
        return new OrderResDtoBuilder();
    }

    public static class OrderResDtoBuilder {
        private UUID orderId;
        private String email;
        private String address;
        private String postcode;
        private OrderStatus orderStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public OrderResDtoBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderResDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public OrderResDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public OrderResDtoBuilder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public OrderResDtoBuilder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderResDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderResDtoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public OrderResDto build() {
            return new OrderResDto(orderId, email, address, postcode, orderStatus, createdAt, updatedAt);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
