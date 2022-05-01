package com.devcourse.coffeeorder.domain.order.dto.order;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;

public class OrderResDto {
    private UUID orderId;
    private String email;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    public OrderResDto(UUID orderId, String email, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.email = email;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public static OrderResDtoBuilder builder() {
        return new OrderResDtoBuilder();
    }

    public static class OrderResDtoBuilder {
        private UUID orderId;
        private String email;
        private OrderStatus orderStatus;
        private LocalDateTime createdAt;

        public OrderResDtoBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderResDtoBuilder email(String email) {
            this.email = email;
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

        public OrderResDto build() {
            return new OrderResDto(orderId, email, orderStatus, createdAt);
        }
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
