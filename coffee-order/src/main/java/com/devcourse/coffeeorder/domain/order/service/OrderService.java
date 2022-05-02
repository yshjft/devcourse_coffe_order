package com.devcourse.coffeeorder.domain.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.dao.order.OrderRepository;
import com.devcourse.coffeeorder.domain.order.dto.order.*;
import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemWithProductResDto;
import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.OrderNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderCreateResDto createOrder(OrderCreateReqDto orderCreateReqDto) {
        Order order = orderCreateReqDto.toEntity();
        List<OrderItem> orderItemList = orderCreateReqDto.getOrderItems().stream()
                .map(orderItemCreateReqDto -> orderItemCreateReqDto.toEntity(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt()))
                .collect(Collectors.toList());

        Order newOrder = orderRepository.create(order);
        orderItemList.forEach(orderItem -> orderItemRepository.create(orderItem));

        return new OrderCreateResDto(newOrder.getOrderId(), newOrder.getCreatedAt());
    }

    public List<OrderResDto> getOrderByStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatus(orderStatus).stream()
                .map(order -> OrderResDto.builder()
                        .orderId(order.getOrderId())
                        .email(order.getEmail())
                        .orderStatus(order.getOrderStatus())
                        .createdAt(order.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public OrderDetailResDto getOrderDetail(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
        List<OrderItem> orderItemList = orderItemRepository.findByIdWithProduct(orderId);

        List<OrderItemWithProductResDto> orderItemDtoList = orderItemList.stream()
                .map(orderItem -> OrderItemWithProductResDto.builder()
                        .orderItemId(orderItem.getOrderItemId())
                        .quantity(orderItem.getQuantity())
                        .productId(orderItem.getProduct().getProductId())
                        .productName(orderItem.getProduct().getProductName())
                        .category(orderItem.getProduct().getCategory())
                        .price(orderItem.getProduct().getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderDetailResDto.builder()
                .orderId(order.getOrderId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postcode(order.getPostcode())
                .orderStatus(order.getOrderStatus())
                .orderItems(orderItemDtoList)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public OrderUpdateResDto updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        order.updateOrderStatus(orderStatus);
        Order updatedOrder = orderRepository.update(order);

        return new OrderUpdateResDto(orderId, updatedOrder.getUpdatedAt());
    }

    @Scheduled(cron = "05 00 14 * * ?")
    public void changeAcceptedToPreparing() {
        LocalDateTime standardTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0, 0, 0));
        orderRepository.orderAcceptedToPreparingForShipment(standardTime);
    }

}
