package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;

public interface OrderItemRepository {
    OrderItem create(OrderItem orderItem);

    List<OrderItem> findAll();

    List<OrderItem> findByOrderIdWithProduct(UUID orderId);
}
