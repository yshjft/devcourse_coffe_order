package com.devcourse.coffeeorder.domain.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.product.entity.Category;

public class ProductResDto {
    private UUID productId;
    private String productName;
    private Category category;
    private long price;
    private LocalDateTime createdAt;

    public ProductResDto(UUID productId, String productName, Category category, long price, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
    }

    public static ProductResDtoBuilder builder() {
        return new ProductResDtoBuilder();
    }

    public static class ProductResDtoBuilder {
        private UUID productId;
        private String productName;
        private Category category;
        private long price;
        private LocalDateTime createdAt;

        public ProductResDtoBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductResDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductResDtoBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductResDtoBuilder price(long price) {
            this.price = price;
            return this;
        }

        public ProductResDtoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductResDto build() {
            return new ProductResDto(productId, productName, category, price, createdAt);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
