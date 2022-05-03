package com.devcourse.coffeeorder.domain.product.entity;

import java.time.LocalDateTime;

public class Category {
    private final String category;
    private final LocalDateTime createdAt;

    public Category(String category) {
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    public Category(String category, LocalDateTime createdAt) {
        this.category = category;
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
