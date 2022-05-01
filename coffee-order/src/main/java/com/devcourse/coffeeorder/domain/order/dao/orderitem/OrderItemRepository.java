package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository {
    OrderItem create(OrderItem orderItem);

    List<OrderItem> findAll();

    List<OrderItem> findByIdWithProduct(UUID orderId);
}
