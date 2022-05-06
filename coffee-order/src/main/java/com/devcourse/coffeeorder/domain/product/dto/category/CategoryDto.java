package com.devcourse.coffeeorder.domain.product.dto.category;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.WrongInputException;
import org.springframework.util.StringUtils;

public class CategoryDto {
    private String category;

    public CategoryDto(String category) {
        validateParam(category);
        this.category = category.trim();
    }

    public String getCategory() {
        return category;
    }

    public Category toEntity() {
        return new Category(category);
    }

    private void validateParam(String category) {
        if(!StringUtils.hasText(category) || category.length() > 50) {
            throw new WrongInputException("category must not be blank! (1 <= category <= 50)");
        }
    }
}
