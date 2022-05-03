package com.devcourse.coffeeorder.domain.product.dto.product;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductResDto {
    private UUID productId;
    private String productName;
    private String category;
    private long price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResDto(UUID productId, String productName, String category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductResDtoBuilder builder() {
        return new ProductResDtoBuilder();
    }

    public static class ProductResDtoBuilder {
        private UUID productId;
        private String productName;
        private String category;
        private long price;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductResDtoBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductResDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductResDtoBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ProductResDtoBuilder price(long price) {
            this.price = price;
            return this;
        }

        public ProductResDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductResDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductResDtoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductResDto build() {
            return new ProductResDto(productId, productName, category, price, description, createdAt, updatedAt);
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
