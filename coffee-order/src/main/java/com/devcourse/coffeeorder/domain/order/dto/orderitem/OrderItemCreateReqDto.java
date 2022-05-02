package com.devcourse.coffeeorder.domain.order.dto.orderitem;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.WrongInputException;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItemCreateReqDto {
    private UUID productId;
    private int quantity;

    public OrderItemCreateReqDto(UUID productId, int quantity) {
        validateParams(productId, quantity);
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem toEntity(UUID orderID, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return OrderItem.builder()
                .orderId(orderID)
                .productId(productId)
                .quantity(quantity)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    private void validateParams(UUID productId, int quantity) {
        if(productId == null) {
            throw new WrongInputException("productId is required property!");
        }
        if(quantity <= 0) {
            throw new WrongInputException("quantity > 0");
        }
    }
}
