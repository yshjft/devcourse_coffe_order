package com.devcourse.coffeeorder.domain.order.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.Order;
import com.devcourse.coffeeorder.domain.order.entity.OrderStatus;

public interface OrderRepository {
    Order create(Order order);

    List<Order> findAll();

    List<Order> findByStatus(OrderStatus orderStatus);

    Optional<Order> findById(UUID orderId);

    List<Order> findByEmail(String email);

    Order update(Order order);

    void orderAcceptedToPreparingForShipment(LocalDateTime time);
}
