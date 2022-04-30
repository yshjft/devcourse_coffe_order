package com.devcourse.coffeeorder.domain.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductUpdateResDto {
    private UUID productId;
    private String productName;
    private LocalDateTime updatedAt;

    public ProductUpdateResDto(UUID productId, String productName, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.updatedAt = updatedAt;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
