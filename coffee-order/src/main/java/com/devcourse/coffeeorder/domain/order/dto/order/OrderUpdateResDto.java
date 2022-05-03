package com.devcourse.coffeeorder.domain.order.dto.order;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderUpdateResDto {
    private UUID orderId;
    private LocalDateTime updatedAt;

    public OrderUpdateResDto(UUID orderId, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.updatedAt = updatedAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
