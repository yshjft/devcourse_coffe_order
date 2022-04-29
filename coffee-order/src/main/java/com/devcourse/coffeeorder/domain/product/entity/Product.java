package com.devcourse.coffeeorder.domain.product.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    private final UUID productId;
    private String productName;
    private Category category;
    private long price;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(UUID productId, String productName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private UUID productId;
        private String productName;
        private Category category;
        private long price;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder price(long price) {
            this.price = price;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Product build() {
            return new Product(this.productId, this.productName, this.category, this.price, this.description, this.createdAt, this.updatedAt);
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

    public void updateProductName(String productName) {
        this.productName = productName;
        this.updatedAt =  LocalDateTime.now();
    }

    public void updateCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePrice(long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
