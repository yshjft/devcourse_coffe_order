package com.devcourse.coffeeorder.domain.order.dao.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;

public interface OrderRepository {
    Order create(Order order);

    List<Order> findAll();

    List<Order> findByStatus(OrderStatus orderStatus);

    void updateStatusById(OrderStatus orderStatus, UUID orderId);

    void orderAcceptedToPreparingForShipment(LocalDateTime time);
}
