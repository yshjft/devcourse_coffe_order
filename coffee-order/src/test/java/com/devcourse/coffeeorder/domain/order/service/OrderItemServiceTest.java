package com.devcourse.coffeeorder.domain.order.service;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.badrequest.OrderException;
import com.devcourse.coffeeorder.global.exception.notfound.NotFoundException;
import com.devcourse.coffeeorder.global.exception.notfound.OrderItemNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    @InjectMocks
    OrderItemService orderItemService;

    @Mock
    OrderItemRepository orderItemRepository;

    private Long orderItemId = 0L;
    private Order cancelledOrder = Order.builder()
            .orderStatus(OrderStatus.ORDER_CANCELLED)
            .build();
    private Order acceptedOrder = Order.builder()
            .orderStatus(OrderStatus.ORDER_ACCEPTED)
            .build();
    private OrderItem orderItemCanceledOrder = OrderItem.builder()
            .orderItemId(1L)
            .order(cancelledOrder)
            .build();
    private OrderItem orderItemAccepted = OrderItem.builder()
            .orderItemId(2L)
            .order(acceptedOrder)
            .build();


    @Test
    @DisplayName("주문 상품 수정 NotFoundException 테스트")
    void testUpdateNotFoundException() {
        try {
            when(orderItemRepository.findByOrderItemIdWithOrder(orderItemId)).thenThrow(new OrderItemNotFoundException());

            orderItemService.updateOrderItemQuantity(orderItemId, 20);
        }catch (NotFoundException e) {
            verify(orderItemRepository, never()).update(any());
        }
    }

    @Test
    @DisplayName("주문 상품 수정 OrderUpdateException 테스트")
    void testUpdateOrderUpdateException() {
        try {
            when(orderItemRepository.findByOrderItemIdWithOrder(orderItemCanceledOrder.getOrderItemId())).thenThrow(new OrderException());

            orderItemService.updateOrderItemQuantity(orderItemCanceledOrder.getOrderItemId(), 10);
        }catch (OrderException e) {
            verify(orderItemRepository, never()).update(orderItemCanceledOrder);
        }
    }

    @Test
    @DisplayName("주문 상품 수정 테스트")
    void testUpdate() {
        when(orderItemRepository.findByOrderItemIdWithOrder(orderItemAccepted.getOrderItemId())).thenReturn(Optional.of(orderItemAccepted));
        when(orderItemRepository.update(orderItemAccepted)).thenReturn(orderItemAccepted);

        orderItemService.updateOrderItemQuantity(orderItemAccepted.getOrderItemId(), 20);

        verify(orderItemRepository).findByOrderItemIdWithOrder(orderItemAccepted.getOrderItemId());
        verify(orderItemRepository).update(orderItemAccepted);
    }


    @Test
    @DisplayName("주문 상품 삭제 NotFoundException 테스트")
    void testDeleteNotFoundException() {
        try {
            when(orderItemRepository.findByOrderItemIdWithOrder(orderItemId)).thenThrow(new OrderItemNotFoundException());

            orderItemService.deleteOrderItem(orderItemId);
        }catch (NotFoundException e) {
            verify(orderItemRepository, never()).delete(any());
        }
    }

    @Test
    @DisplayName("주문 상품 삭제 OrderUpdateException 테스트")
    void testDeleteOrderUpdateException() {
        try {
            when(orderItemRepository.findByOrderItemIdWithOrder(orderItemCanceledOrder.getOrderItemId())).thenThrow(new OrderException());

            orderItemService.deleteOrderItem(orderItemCanceledOrder.getOrderItemId());
        }catch (OrderException e) {
            verify(orderItemRepository, never()).delete(orderItemCanceledOrder);
        }
    }

    @Test
    @DisplayName("주문 상품 삭제 테스트")
    void testDeleteUpdate() {
        when(orderItemRepository.findByOrderItemIdWithOrder(orderItemAccepted.getOrderItemId())).thenReturn(Optional.of(orderItemAccepted));

        orderItemService.deleteOrderItem(orderItemAccepted.getOrderItemId());

        verify(orderItemRepository).findByOrderItemIdWithOrder(orderItemAccepted.getOrderItemId());
        verify(orderItemRepository).delete(orderItemAccepted);
    }



}