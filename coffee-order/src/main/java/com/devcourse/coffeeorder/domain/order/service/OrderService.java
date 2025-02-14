package com.devcourse.coffeeorder.domain.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devcourse.coffeeorder.domain.order.dto.order.*;
import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.order.dao.order.OrderRepository;
import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemWithProductDetailResDto;
import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemWithProductResDto;
import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.domain.product.dao.product.ProductRepository;
import com.devcourse.coffeeorder.global.common.MetaData;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.OrderNotFoundException;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.OrderException;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.ProductNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus.ORDER_CANCELLED;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    /**
     * 주문 생성
     */
    @Transactional
    public OrderCreateResDto createOrder(OrderCreateReqDto orderCreateReqDto) {
        Order order = orderCreateReqDto.toEntity();

        List<OrderItem> orderItemList = new ArrayList<>();
        orderCreateReqDto.getOrderItems().forEach(orderItemCreateReqDto -> {
            productRepository.findById(orderItemCreateReqDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(String.format("can't find a product(%s)", orderItemCreateReqDto.getProductId().toString())));

            orderItemList.add(orderItemCreateReqDto.toEntity(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt()));
        });

        Order newOrder = orderRepository.create(order);
        orderItemList.forEach(orderItem -> orderItemRepository.create(orderItem));

        return new OrderCreateResDto(newOrder.getOrderId(), newOrder.getCreatedAt());
    }

    /**
     * 주문 상태를 이용한 주문 목록 조회
     */
    public OrdersResDto getOrdersByStatus(OrderStatus orderStatus) {
        List<OrderResDto> orderResDtoList = orderRepository.findByStatus(orderStatus).stream()
                .map(order -> OrderResDto.builder()
                        .orderId(order.getOrderId())
                        .email(order.getEmail())
                        .orderStatus(order.getOrderStatus())
                        .createdAt(order.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        MetaData metaData = new MetaData(orderResDtoList.size());

        return new OrdersResDto(metaData, orderResDtoList);
    }

    /**
     * email을 이용한 주문 목록 조회
     */
    public OrdersResDto getOrdersByEmail(String email) {
        List<Order> orderList = orderRepository.findByEmail(email);

        List<OrderResDto> orderResDtoList = new ArrayList<>();
        orderList.forEach(order -> {
            List<OrderItemWithProductResDto> orderItems = findByOrderIdWithProduct(order.getOrderId())
                    .stream().map(orderItem -> OrderItemWithProductResDto.builder()
                            .quantity(orderItem.getQuantity())
                            .productName(orderItem.getProduct().getProductName())
                            .build())
                    .collect(Collectors.toList());

            OrderResDto orderResDto = OrderResDto.builder()
                    .orderId(order.getOrderId())
                    .email(order.getEmail())
                    .orderStatus(order.getOrderStatus())
                    .orderItems(orderItems)
                    .createdAt(order.getCreatedAt())
                    .build();

            orderResDtoList.add(orderResDto);
        });
        MetaData metaData = new MetaData(orderResDtoList.size());

        return new OrdersResDto(metaData, orderResDtoList);
    }

    /**
     * orderId를 이용한 주문 상세 조회
     */
    public OrderDetailResDto getOrder(UUID orderId) {
        Order order = orderFindById(orderId);
        List<OrderItem> orderItemList = findByOrderIdWithProduct(orderId);

        List<OrderItemWithProductDetailResDto> orderItemDtoList = orderItemList.stream()
                .map(orderItem -> OrderItemWithProductDetailResDto.builder()
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

    /**
     * 주문 수정: 주소, 우편번호 수정
     */
    public OrderUpdateResDto updateOrder(UUID orderId, OrderUpdateReqDto orderUpdateReqDto) {
        Order order = orderFindById(orderId);

        if(!order.isUpdatable()) {
            throw new OrderException(String.format("you can't update %s order", order.getOrderStatus()));
        }

        order.updateAddress(orderUpdateReqDto.getAddress());
        order.updatePostcode(orderUpdateReqDto.getPostcode());
        Order updatedOrder = orderRepository.update(order);

        return new OrderUpdateResDto(orderId, updatedOrder.getUpdatedAt());
    }

    /**
     * 주문 상태 수정
     */
    public OrderUpdateResDto updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        Order order = orderFindById(orderId);

        order.updateOrderStatus(orderStatus);
        Order updatedOrder = updateOrder(order);

        return new OrderUpdateResDto(orderId, updatedOrder.getUpdatedAt());
    }

    /**
     * 주문 취소
     */
    public OrderUpdateResDto cancelOrder(UUID orderId) {
        Order order = orderFindById(orderId);

        if(!order.isUpdatable()) {
            throw new OrderException(String.format("you can't cancel %s order", order.getOrderStatus()));
        }

        order.updateOrderStatus(ORDER_CANCELLED);
        Order updatedOrder = updateOrder(order);

        return new OrderUpdateResDto(orderId, updatedOrder.getUpdatedAt());
    }

    /**
     * 매일 오후 2시에 ORDER_ACCEPTED 이면서 당일 오후 2시 이전에 생성된 모든 주문의 주문 상태를 PREPARING_FOR_SHIPMENT로 수정
     */
    @Scheduled(cron = "05 00 14 * * ?")
    public void changeAcceptedToPreparing() {
        LocalDateTime standardTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0, 0, 0));
        orderRepository.orderAcceptedToPreparingForShipment(standardTime);
    }

    public void deleteOrder(UUID orderId) {
        Order order = orderFindById(orderId);

        if(order.getOrderStatus() != OrderStatus.ORDER_CANCELLED) {
            throw new OrderException("only cancelled order can be deleted");
        }

        orderRepository.delete(order);
    }

    private List<OrderItem> findByOrderIdWithProduct(UUID orderId) {
        return orderItemRepository.findByOrderIdWithProduct(orderId);
    }

    private Order orderFindById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
    }

    private Order updateOrder(Order order) {
        return orderRepository.update(order);
    }
}
