package com.devcourse.coffeeorder.domain.product.dto;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.WrongInputException;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductCreateReqDto {
    private String productName;
    private Category category;
    private long price;
    private String description;

    public ProductCreateReqDto(String productName, Category category, long price, String description) {
        if(productName == null || productName.equals("")) {
            throw new WrongInputException("productName is required property!");
        }

        if(category == null) {
            throw new WrongInputException("category is required property!");
        }

        if(price < 0) {
            throw new WrongInputException("price accept positive number and 0!");
        }

        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
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

    public Product toEntity() {
        return Product.builder()
                .productId(UUID.randomUUID())
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
