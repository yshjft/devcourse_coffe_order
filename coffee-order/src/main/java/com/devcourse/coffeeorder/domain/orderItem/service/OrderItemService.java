package com.devcourse.coffeeorder.domain.orderItem.service;

import com.devcourse.coffeeorder.domain.orderItem.dao.OrderItemRepository;
import com.devcourse.coffeeorder.domain.orderItem.dto.OrderItemUpdateResDto;
import com.devcourse.coffeeorder.domain.orderItem.entity.OrderItem;
import com.devcourse.coffeeorder.global.exception.notfound.OrderItemNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * 주문 상품 수량 수정, order가 엡데이트가 가능한 경우에만
     **/
    public OrderItemUpdateResDto updateOrderItem(Long orderItemId, int quantity) {
        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(orderItemId).orElseThrow(() -> new OrderItemNotFoundException(orderItemId.toString()));

        orderItem.updateQuantity(quantity);
        OrderItem updatedOrderItem = orderItemRepository.update(orderItem);

        return new OrderItemUpdateResDto(
                updatedOrderItem.getOrderItemId(),
                updatedOrderItem.getUpdatedAt());
    }

    /**
     * 주문 상품 수량 제거, order가 엡데이트가 가능한 경우에만
     **/
}
