package com.devcourse.coffeeorder.domain.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.dao.order.OrderRepository;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderCreateReqDto;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderCreateResDto;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderResDto;
import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
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
        List<OrderItem> orderItems =  orderCreateReqDto.getOrderItems().stream()
                .map(orderItemCreateReqDto -> orderItemCreateReqDto.toEntity(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt()))
                .collect(Collectors.toList());

        Order newOrder = orderRepository.create(order);
        orderItems.forEach(orderItem -> orderItemRepository.create(orderItem));

        return new OrderCreateResDto(newOrder.getOrderId(), newOrder.getCreatedAt());
    }

    public List<OrderResDto> getOrderByStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatus(orderStatus).stream()
                .map(order -> OrderResDto.builder()
                        .orderId(order.getOrderId())
                        .email(order.getEmail())
                        .address(order.getAddress())
                        .postcode(order.getPostcode())
                        .orderStatus(order.getOrderStatus())
                        .createdAt(order.getCreatedAt())
                        .updatedAt(order.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "05 00 14 * * ?")
    public void changeAcceptedToPreparing() {
        LocalDateTime standardTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0, 0, 0));
        orderRepository.orderAcceptedToPreparingForShipment(standardTime);
    }

}
