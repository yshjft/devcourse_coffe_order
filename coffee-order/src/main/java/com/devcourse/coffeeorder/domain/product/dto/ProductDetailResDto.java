package com.devcourse.coffeeorder.domain.product.dto;

import com.devcourse.coffeeorder.domain.product.entity.Category;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDetailResDto {
    private UUID productId;
    private String productName;
    private Category category;
    private long price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDetailResDto(UUID productId, String productName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductDetailResDtoBuilder builder() {
        return new ProductDetailResDtoBuilder();
    }

    public static class ProductDetailResDtoBuilder {
        private UUID productId;
        private String productName;
        private Category category;
        private long price;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductDetailResDtoBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductDetailResDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductDetailResDtoBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductDetailResDtoBuilder price(long price) {
            this.price = price;
            return this;
        }

        public ProductDetailResDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductDetailResDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductDetailResDtoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductDetailResDto build() {
            return new ProductDetailResDto(productId, productName, category, price, description, createdAt, updatedAt);
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Category getCategory() {
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
