package com.devcourse.coffeeorder.domain.order.dto.order;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderCreateResDto {
    private UUID orderId;
    private LocalDateTime createdAt;

    public OrderCreateResDto(UUID orderId, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.createdAt = createdAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
