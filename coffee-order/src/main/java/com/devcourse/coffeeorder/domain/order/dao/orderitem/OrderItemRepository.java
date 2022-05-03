package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;

public interface OrderItemRepository {
    /**
     * 생성
     */
    OrderItem create(OrderItem orderItem);

    /**
     * 조회
     */
    List<OrderItem> findAll();

    /**
     * orderId를 이용하여 주문 상품과 상품에 대해 한번에 조회
     */
    List<OrderItem> findByOrderIdWithProduct(UUID orderId);

    /**
     * productId를 이용하여 주문 상품을 조회
     */
    List<OrderItem> findByProductId(UUID productId);

    /**
     * orderItemId를 이용하여 주문 상품과 주문에 대해 한번에 조회
     */
    Optional<OrderItem> findByOrderItemIdWithOrder(Long orderItemId);

    /**
     * 수정
     */
    OrderItem update(OrderItem orderItem);

    /**
     * 삭제
     */
    void delete(OrderItem orderItem);
}
