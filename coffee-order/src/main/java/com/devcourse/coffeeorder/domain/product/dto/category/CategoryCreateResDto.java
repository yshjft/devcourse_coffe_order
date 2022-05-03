package com.devcourse.coffeeorder.domain.product.dto.category;

import java.time.LocalDateTime;

public class CategoryCreateResDto {
    private String category;
    private LocalDateTime createdAt;

    public CategoryCreateResDto(String category, LocalDateTime createdAt) {
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
