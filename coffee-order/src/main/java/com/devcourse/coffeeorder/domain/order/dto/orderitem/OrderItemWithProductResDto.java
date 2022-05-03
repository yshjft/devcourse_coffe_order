package com.devcourse.coffeeorder.domain.order.dto.orderitem;

public class OrderItemWithProductResDto {
    private int quantity;
    private String productName;

    public OrderItemWithProductResDto(int quantity, String productName) {
        this.quantity = quantity;
        this.productName = productName;
    }

    public static OrderItemWithProductResDtoBuilder builder() {
        return new OrderItemWithProductResDtoBuilder();
    }

    public static class OrderItemWithProductResDtoBuilder {
        private int quantity;
        private String productName;

        public OrderItemWithProductResDtoBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemWithProductResDtoBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public OrderItemWithProductResDto build() {
            return new OrderItemWithProductResDto(quantity, productName);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
