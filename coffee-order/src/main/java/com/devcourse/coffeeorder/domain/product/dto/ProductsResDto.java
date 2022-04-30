package com.devcourse.coffeeorder.domain.product.dto;

import java.util.List;

import com.devcourse.coffeeorder.global.common.MetaData;

public class ProductsResDto {
    private MetaData metaData;
    private List<ProductResDto> products;

    public ProductsResDto(MetaData metaData, List<ProductResDto> products) {
        this.metaData = metaData;
        this.products = products;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<ProductResDto> getProducts() {
        return products;
    }
}
