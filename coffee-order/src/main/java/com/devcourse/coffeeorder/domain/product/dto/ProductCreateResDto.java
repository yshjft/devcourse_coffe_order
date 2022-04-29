package com.devcourse.coffeeorder.domain.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductCreateResDto {
    private UUID productId;
    private String productName;
    private LocalDateTime createdAt;

    public ProductCreateResDto(UUID productId, String productName, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.createdAt = createdAt;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
