package com.devcourse.coffeeorder.domain.orderItem.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.orderItem.entity.OrderItem;

public interface OrderItemRepository {
    OrderItem create(OrderItem orderItem);

    List<OrderItem> findAll();

    List<OrderItem> findByOrderIdWithProduct(UUID orderId);

    Optional<OrderItem> findByOrderItemIdWithOrder(Long orderItemId);

    OrderItem update(OrderItem orderItem);

    void delete(Long orderItemId);
}
