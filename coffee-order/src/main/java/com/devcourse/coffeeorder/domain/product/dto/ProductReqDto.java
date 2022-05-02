package com.devcourse.coffeeorder.domain.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.WrongInputException;
import org.springframework.util.StringUtils;

public class ProductReqDto {
    private String productName;
    private Category category;
    private long price;
    private String description;

    public ProductReqDto(String productName, Category category, long price, String description) {
        if(!StringUtils.hasText(productName)) {
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

    public static ProductReqDtoBuilder builder() {
        return new ProductReqDtoBuilder();
    }

    public static class ProductReqDtoBuilder {
        private String productName;
        private Category category;
        private long price;
        private String description;

        public ProductReqDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductReqDtoBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductReqDtoBuilder price(long price) {
            this.price = price;
            return this;
        }

        public ProductReqDtoBuilder description (String description) {
            this.description = description;
            return this;
        }

        public ProductReqDto build() {
            return new ProductReqDto(productName, category, price, description);
        }
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
