package com.devcourse.coffeeorder.domain.product.dto;

import java.util.List;



public class ProductsResDto {
    private long total;
    private List<ProductResDto> products;

    public ProductsResDto(long total, List<ProductResDto> products) {
        this.total = total;
        this.products = products;
    }

    public long getTotal() {
        return total;
    }

    public List<ProductResDto> getProducts() {
        return products;
    }
}
