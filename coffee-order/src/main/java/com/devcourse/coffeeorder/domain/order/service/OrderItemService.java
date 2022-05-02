package com.devcourse.coffeeorder.domain.order.service;

import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * 주문 상품 수량 수정, order 상태가
     **/

    // 삭제
}
