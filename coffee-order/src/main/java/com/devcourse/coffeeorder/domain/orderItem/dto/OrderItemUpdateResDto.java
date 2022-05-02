package com.devcourse.coffeeorder.domain.orderItem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItemUpdateResDto {
    private Long orderItemId;
    private LocalDateTime updatedAt;

    public OrderItemUpdateResDto(Long orderItemId, LocalDateTime updatedAt) {
        this.orderItemId = orderItemId;
        this.updatedAt = updatedAt;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
