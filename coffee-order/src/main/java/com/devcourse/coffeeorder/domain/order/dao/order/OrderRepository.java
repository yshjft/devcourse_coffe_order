package com.devcourse.coffeeorder.domain.order.dao.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;

public interface OrderRepository {
    /**
     * 생성
     */
    Order create(Order order);

    /**
     * 전체 조회
     */
    List<Order> findAll();

    /**
     * orderStatus를 이용한 조회
     */
    List<Order> findByStatus(OrderStatus orderStatus);

    /**
     * orderId를 이용한 조회
     */
    Optional<Order> findById(UUID orderId);

    /**
     * email를 이용한 조회
     */
    List<Order> findByEmail(String email);

    /**
     * 수정
     */
    Order update(Order order);

    /**
     * ORDER_ACCPETED -> PREPARING_FOR_SHIPMENT
     */
    void orderAcceptedToPreparingForShipment(LocalDateTime time);
}
