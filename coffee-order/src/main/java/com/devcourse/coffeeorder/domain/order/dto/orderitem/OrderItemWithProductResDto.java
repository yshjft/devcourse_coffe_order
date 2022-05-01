package com.devcourse.coffeeorder.domain.order.dto.orderitem;

import com.devcourse.coffeeorder.domain.product.entity.Category;

import java.util.UUID;

public class OrderItemWithProductResDto {
    private Long orderItemId;
    private int quantity;
    private UUID productId;
    private String productName;
    private Category category;
    private long price;

    public OrderItemWithProductResDto(Long orderItemId, int quantity, UUID productId, String productName, Category category, long price) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    public static OrderItemWithProductResDtoBuilder builder() {
        return new OrderItemWithProductResDtoBuilder();
    }

    public static class OrderItemWithProductResDtoBuilder {
        private Long orderItemId;
        private int quantity;
        private UUID productId;
        private String productName;
        private Category category;
        private long price;

        public OrderItemWithProductResDtoBuilder orderItemId(Long orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public OrderItemWithProductResDtoBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemWithProductResDtoBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemWithProductResDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public OrderItemWithProductResDtoBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public OrderItemWithProductResDtoBuilder price(long price) {
            this.price = price;
            return this;
        }

        public OrderItemWithProductResDto build() {
            return new OrderItemWithProductResDto(orderItemId, quantity, productId, productName, category, price);
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
