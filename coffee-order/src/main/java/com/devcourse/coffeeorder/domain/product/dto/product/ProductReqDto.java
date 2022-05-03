package com.devcourse.coffeeorder.domain.product.dto.product;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.WrongInputException;
import org.springframework.util.StringUtils;

public class ProductReqDto {
    private String productName;
    private String category;
    private long price;
    private String description;

    public ProductReqDto(String productName, String category, long price, String description) {
        validateParams(productName, category, price);
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
        private String category;
        private long price;
        private String description;

        public ProductReqDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductReqDtoBuilder category(String category) {
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

    public String getCategory() {
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

    private void validateParams(String productName, String category, long price) {
        if(!StringUtils.hasText(productName) || productName.length() > 50) {
            throw new WrongInputException("productName must not be blank! (1 <= productName <= 50)");
        }

        if(!StringUtils.hasText(category) || category.length() > 50) {
            throw new WrongInputException("category must not be blank! (1 <= category <= 50)");
        }

        if(price < 0) {
            throw new WrongInputException("price >= 0");
        }
    }
}
