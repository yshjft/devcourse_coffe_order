package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    OrderItem create(OrderItem orderItem);

    List<OrderItem> findAll();
}
