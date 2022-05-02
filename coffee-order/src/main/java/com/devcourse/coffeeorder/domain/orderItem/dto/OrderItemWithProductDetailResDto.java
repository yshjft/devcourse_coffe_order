package com.devcourse.coffeeorder.domain.orderItem.dto;

import com.devcourse.coffeeorder.domain.product.entity.Category;

import java.util.UUID;

public class OrderItemWithProductDetailResDto {
    private Long orderItemId;
    private int quantity;
    private UUID productId;
    private String productName;
    private Category category;
    private long price;

    public OrderItemWithProductDetailResDto(Long orderItemId, int quantity, UUID productId, String productName, Category category, long price) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    public static OrderItemWithProductDetailResDtoBuilder builder() {
        return new OrderItemWithProductDetailResDtoBuilder();
    }

    public static class OrderItemWithProductDetailResDtoBuilder {
        private Long orderItemId;
        private int quantity;
        private UUID productId;
        private String productName;
        private Category category;
        private long price;

        public OrderItemWithProductDetailResDtoBuilder orderItemId(Long orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public OrderItemWithProductDetailResDtoBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemWithProductDetailResDtoBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemWithProductDetailResDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public OrderItemWithProductDetailResDtoBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public OrderItemWithProductDetailResDtoBuilder price(long price) {
            this.price = price;
            return this;
        }

        public OrderItemWithProductDetailResDto build() {
            return new OrderItemWithProductDetailResDto(orderItemId, quantity, productId, productName, category, price);
        }
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
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
}
