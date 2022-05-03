package com.devcourse.coffeeorder.domain.order.entity.orderitem;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.product.entity.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItem {
    private final Long orderItemId;
    private final UUID orderId;
    private final UUID productId;
    private int quantity;
    private final Product product;
    private final Order order;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderItem(Long orderItemId, UUID orderId, UUID productId, int quantity, Product product, Order order, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public static class OrderItemBuilder {
        private Long orderItemId;
        private UUID orderId;
        private UUID productId;
        private int quantity;
        private Product product;
        private Order order;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public OrderItemBuilder orderItemId(Long orderItemId) {
            this.orderItemId = orderItemId;
            return this;
        }

        public OrderItemBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderItemBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderItemBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(orderItemId, orderId, productId, quantity, product, order, createdAt, updatedAt);
        }
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
        this.updatedAt = LocalDateTime.now();
    }
}
