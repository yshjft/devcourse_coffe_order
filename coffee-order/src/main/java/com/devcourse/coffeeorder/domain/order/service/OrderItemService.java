package com.devcourse.coffeeorder.domain.order.service;

import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemUpdateResDto;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.OrderItemException;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.OrderItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new OrderItemException(String.format("you can't update %s orderItem", orderItem.getOrder().getOrderStatus()));
        }

        orderItem.updateQuantity(quantity);
        OrderItem updatedOrderItem = orderItemRepository.update(orderItem);

        return new OrderItemUpdateResDto(
                updatedOrderItem.getOrderItemId(),
                updatedOrderItem.getUpdatedAt());
    }

    /**
     * 주문 상품 삭제
     **/
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findByOrderItemIdWithOrder(orderItemId).orElseThrow(() -> new OrderItemNotFoundException(orderItemId.toString()));

        if(!orderItem.getOrder().isUpdatable()) {
            throw new OrderItemException(String.format("you can't delete %s orderItem", orderItem.getOrder().getOrderStatus()));
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderIdWithProduct(orderItem.getOrderId());
        if(orderItems.size() == 1){
            throw new OrderItemException(String.format("you can't delete orderItem. Because Order(%s) has only one Item.", orderItem.getOrderId().toString()));
        }

        orderItemRepository.delete(orderItem);
    }
}
