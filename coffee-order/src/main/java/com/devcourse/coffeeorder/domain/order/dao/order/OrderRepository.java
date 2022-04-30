package com.devcourse.coffeeorder.domain.order.dao.order;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;

import java.util.List;

public interface OrderRepository {
    Order create(Order order);

    List<Order> findAll();
}
