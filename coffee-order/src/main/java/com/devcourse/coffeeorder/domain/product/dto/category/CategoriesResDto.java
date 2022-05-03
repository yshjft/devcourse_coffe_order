package com.devcourse.coffeeorder.domain.product.dto.category;

import com.devcourse.coffeeorder.global.common.MetaData;

import java.util.List;

public class CategoriesResDto {
    private MetaData metaData;
    private List<CategoryDto> categories;

    public CategoriesResDto(MetaData metaData, List<CategoryDto> categories) {
        this.metaData = metaData;
        this.categories = categories;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }
}
