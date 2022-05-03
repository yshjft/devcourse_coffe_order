package com.devcourse.coffeeorder.domain.orderitem.service;

import com.devcourse.coffeeorder.domain.orderitem.dao.OrderItemRepository;
import com.devcourse.coffeeorder.domain.orderitem.dto.OrderItemUpdateResDto;
import com.devcourse.coffeeorder.domain.orderitem.entity.OrderItem;
import com.devcourse.coffeeorder.global.exception.OrderUpdateException;
import com.devcourse.coffeeorder.global.exception.notfound.OrderItemNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * 주문 상품 수량 수정
     **/
    public OrderItemUpdateResDto updateOrderItemQuantity(Long orderItemId, int quantity) {
        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(orderItemId).orElseThrow(() -> new OrderItemNotFoundException(orderItemId.toString()));

        if(!orderItem.getOrder().isUpdatable()) {
            throw new OrderUpdateException(String.format("you can't update %s orderItem", orderItem.getOrder().getOrderStatus()));
        }

        orderItem.updateQuantity(quantity);
        OrderItem updatedOrderItem = orderItemRepository.update(orderItem);

        return new OrderItemUpdateResDto(
                updatedOrderItem.getOrderItemId(),
                updatedOrderItem.getUpdatedAt());
    }

    /**
     * 주문 상품 수량 제거
     **/
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(orderItemId).orElseThrow(() -> new OrderItemNotFoundException(orderItemId.toString()));

        if(!orderItem.getOrder().isUpdatable()) {
            throw new OrderUpdateException(String.format("you can't delete %s orderItem", orderItem.getOrder().getOrderStatus()));
        }

        orderItemRepository.delete(orderItem);
    }
}
