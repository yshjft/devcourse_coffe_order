package com.devcourse.coffeeorder.domain.product.dto.category;

import com.devcourse.coffeeorder.domain.product.entity.Category;

public class CategoryDto {
    private String category;

    public CategoryDto(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public Category toEntity() {
        return new Category(category);
    }
}
