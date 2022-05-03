package com.devcourse.coffeeorder.domain.product.dto.category;

import java.time.LocalDateTime;

public class CategoryDetailResDto {
    private String category;
    private LocalDateTime createdAt;

    public CategoryDetailResDto(String category, LocalDateTime createdAt) {
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
