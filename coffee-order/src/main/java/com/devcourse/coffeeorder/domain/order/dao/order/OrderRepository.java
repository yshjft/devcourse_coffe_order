package com.devcourse.coffeeorder.domain.order.dao.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;

public interface OrderRepository {
    Order create(Order order);

    List<Order> findAll();

    List<Order> findByStatus(OrderStatus orderStatus);

    Optional<Order> findById(UUID orderId);

    Order update(Order order);

    void orderAcceptedToPreparingForShipment(LocalDateTime time);
}
