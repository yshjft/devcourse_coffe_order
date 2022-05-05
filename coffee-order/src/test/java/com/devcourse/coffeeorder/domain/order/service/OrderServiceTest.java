package com.devcourse.coffeeorder.domain.order.service;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.dao.order.OrderRepository;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderCreateReqDto;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderUpdateReqDto;
import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemCreateReqDto;
import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.domain.product.dao.product.ProductRepository;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.OrderNotFoundException;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.OrderException;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @Mock
    ProductRepository productRepository;

    private UUID orderId = UUID.randomUUID();

    private Product product = Product.builder().build();

    private OrderItemCreateReqDto orderItemCreateReqDto = new OrderItemCreateReqDto(UUID.randomUUID(), 10);
    private OrderCreateReqDto orderCreateReqDto = OrderCreateReqDto.builder()
            .email("test@test.com")
            .address("aa")
            .postcode("123")
            .orderItems(Arrays.asList(orderItemCreateReqDto))
            .build();

    private OrderItem orderItem = OrderItem.builder()
            .product(product)
            .build();

    private Order orderAccepted = Order.builder()
            .orderId(UUID.randomUUID())
            .email("test@test.com")
            .orderStatus(OrderStatus.ORDER_ACCEPTED)
            .build();

    private Order orderCancelled = Order.builder()
            .orderId(UUID.randomUUID())
            .email("test@test.com")
            .orderStatus(OrderStatus.ORDER_CANCELLED)
            .build();

    private OrderUpdateReqDto orderUpdateReqDto = new OrderUpdateReqDto("경기도 수원시 ", "00000");

    @Test
    @DisplayName("주문 생성 ProductNotFoundException 테스트")
    void testGetOrder() {
        try {
            when(productRepository.findById(orderItemCreateReqDto.getProductId())).thenThrow(new ProductNotFoundException());

            orderService.createOrder(orderCreateReqDto);
        }catch (ProductNotFoundException e) {
            verify(orderItemRepository, never()).create(any());
        }
    }


    @Test
    @DisplayName("주문 상세 조회 OrderNotFoundException 테스트")
    void testGetOrderOrderNotFoundException() {
        try {
            when(orderRepository.findById(orderId)).thenThrow(new OrderNotFoundException());

            orderService.getOrder(orderId);
        }catch (OrderNotFoundException e) {
            verify(orderItemRepository, never()).findByOrderIdWithProduct(orderId);
        }
    }

    @Test
    @DisplayName("주문 상세 조회 테스트")
    void testGetOrderDetail() {
        when(orderRepository.findById(orderAccepted.getOrderId())).thenReturn(Optional.of(orderAccepted));
        when(orderItemRepository.findByOrderIdWithProduct(orderAccepted.getOrderId())).thenReturn(Arrays.asList(orderItem));

        orderService.getOrder(orderAccepted.getOrderId());

        verify(orderItemRepository).findByOrderIdWithProduct(orderAccepted.getOrderId());
    }

    @Test
    @DisplayName("email을 이용한 주문 조회 OrderNotFoundException 테스트")
    void testGetOrdersByEmailOrderNotFoundException() {
        try{
            when(orderRepository.findByEmail(orderAccepted.getEmail())).thenThrow(new OrderNotFoundException());
            orderService.getOrdersByEmail(orderAccepted.getEmail());
        }catch (OrderNotFoundException e) {
            verify(orderItemRepository, never()).findByOrderIdWithProduct(orderAccepted.getOrderId());
        }
    }

    @Test
    @DisplayName("email을 이용한 주문 조회 테스트")
    void testGetOrdersByEmail() {
        when(orderRepository.findByEmail(orderAccepted.getEmail())).thenReturn(Arrays.asList(orderAccepted));
        when(orderItemRepository.findByOrderIdWithProduct(orderAccepted.getOrderId())).thenReturn(Arrays.asList(orderItem));

        orderService.getOrdersByEmail(orderAccepted.getEmail());

        verify(orderItemRepository).findByOrderIdWithProduct(orderAccepted.getOrderId());
    }

    @Test
    @DisplayName("주문 수정 OrderNotFoundException 테스트")
    void testUpdateOrderOrderNotFoundException() {
        try{
            when(orderRepository.findById(orderAccepted.getOrderId())).thenThrow(new OrderNotFoundException());

            orderService.updateOrder(orderAccepted.getOrderId(), orderUpdateReqDto);
        }catch (OrderNotFoundException e) {
            verify(orderRepository, never()).update(any());
        }
    }

    @Test
    @DisplayName("주문 수정 OrderUpdateException 테스트")
    void testUpdateOrderOrderUpdateException() {
        try{
            when(orderRepository.findById(orderCancelled.getOrderId())).thenReturn(Optional.of(orderCancelled));

            orderService.updateOrder(orderCancelled.getOrderId(), orderUpdateReqDto);
        }catch (OrderException e) {
            verify(orderRepository, never()).update(any());
        }
    }

    @Test
    @DisplayName("주문 수정 테스트")
    void testUpdateOrder() {
        when(orderRepository.findById(orderAccepted.getOrderId())).thenReturn(Optional.of(orderAccepted));
        when(orderRepository.update(orderAccepted)).thenReturn(orderAccepted);

        orderService.updateOrder(orderAccepted.getOrderId(), orderUpdateReqDto);

        verify(orderRepository).update(any());
    }

    @Test
    @DisplayName("주문 삭제 예외 테스트")
    void testDeleteOrderException() {
        try{
            when(orderRepository.findById(orderAccepted.getOrderId())).thenReturn(Optional.of(orderAccepted));

            orderService.deleteOrder(orderAccepted.getOrderId());
        }catch (OrderException e) {
            verify(orderRepository, never()).delete(orderAccepted);
        }
    }

    @Test
    @DisplayName("주문 삭제 테스트")
    void testDeleteOrder() {
        when(orderRepository.findById(orderCancelled.getOrderId())).thenReturn(Optional.of(orderCancelled));

        orderService.deleteOrder(orderCancelled.getOrderId());

        verify(orderRepository).delete(orderCancelled);
    }
}