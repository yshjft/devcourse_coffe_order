package com.devcourse.coffeeorder.domain.order.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemWithProductResDto;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResDto {
    private UUID orderId;
    private String email;
    private OrderStatus orderStatus;
    private List<OrderItemWithProductResDto> orderItems;
    private LocalDateTime createdAt;

    public OrderResDto(UUID orderId, String email, OrderStatus orderStatus, List<OrderItemWithProductResDto> orderItems, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.email = email;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.createdAt = createdAt;
    }

    public static OrderResDtoBuilder builder() {
        return new OrderResDtoBuilder();
    }

    public static class OrderResDtoBuilder {
        private UUID orderId;
        private String email;
        private OrderStatus orderStatus;
        private List<OrderItemWithProductResDto> orderItems;
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

        public OrderResDtoBuilder orderItems(List<OrderItemWithProductResDto> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public OrderResDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderResDto build() {
            return new OrderResDto(orderId, email, orderStatus, orderItems, createdAt);
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

    public List<OrderItemWithProductResDto> getOrderItems() {
        return orderItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
