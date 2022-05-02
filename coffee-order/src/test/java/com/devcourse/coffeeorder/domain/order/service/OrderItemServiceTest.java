package com.devcourse.coffeeorder.domain.order.service;

import static com.devcourse.coffeeorder.TestData.orderItem3;
import static org.mockito.Mockito.*;

import com.devcourse.coffeeorder.domain.orderItem.dao.OrderItemRepository;
import com.devcourse.coffeeorder.domain.orderItem.service.OrderItemService;
import com.devcourse.coffeeorder.global.exception.notfound.NotFoundException;
import com.devcourse.coffeeorder.global.exception.notfound.OrderItemNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    @InjectMocks
    OrderItemService orderItemService;

    @Mock
    OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("주문 상품 수정 예외 테스트")
    void testUpdateException() {
        try {
            when(orderItemRepository.findByOrderItemIdWithOrder(orderItem3.getOrderItemId())).thenThrow(new OrderItemNotFoundException());

            orderItemService.updateOrderItem(orderItem3.getOrderItemId(), 20);
        }catch (NotFoundException e) {
            verify(orderItemRepository, never()).update(orderItem3);
        }
    }

    @Test
    @DisplayName("주문 상품 수정 예외")
    void testUpdate() {
        when(orderItemRepository.findByOrderItemIdWithOrder(orderItem3.getOrderItemId())).thenReturn(Optional.of(orderItem3));
        when(orderItemRepository.update(orderItem3)).thenReturn(orderItem3);

        orderItemService.updateOrderItem(orderItem3.getOrderItemId(), 20);

        verify(orderItemRepository).findByOrderItemIdWithOrder(orderItem3.getOrderItemId());
        verify(orderItemRepository).update(orderItem3);
    }
}