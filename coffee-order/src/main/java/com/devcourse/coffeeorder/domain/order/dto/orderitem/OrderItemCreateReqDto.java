package com.devcourse.coffeeorder.domain.order.dto.orderitem;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.WrongInputException;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItemCreateReqDto {
    private UUID productId;
    private int quantity;

    public OrderItemCreateReqDto(UUID productId, int quantity) {
        if(productId == null) {
            throw new WrongInputException("productId is required property!");
        }
        this.productId = productId;

        if(quantity <= 0) {
            throw new WrongInputException("quantity > 0");
        }
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
}
