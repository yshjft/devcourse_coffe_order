package com.devcourse.coffeeorder.domain.order.dto.orderitem;

import java.time.LocalDateTime;

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
