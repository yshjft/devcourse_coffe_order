package com.devcourse.coffeeorder.domain.order.controller;

import static com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus.*;

import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.dto.order.*;
import com.devcourse.coffeeorder.domain.order.service.OrderService;
import com.devcourse.coffeeorder.global.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(@RequestBody OrderCreateReqDto orderCreateReqDto) {
        OrderCreateResDto orderCreateResDto = orderService.createOrder(orderCreateReqDto);

        ResponseDto responseDto = new ResponseDto<>(HttpStatus.CREATED.value(), "create order successfully", orderCreateResDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getOrdersByEmail(@RequestParam String email) {
        OrdersResDto ordersResDto = orderService.getOrdersByEmail(email);

        ResponseDto responseDto = new ResponseDto<>(HttpStatus.OK.value(), "get orders successfully", ordersResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto> getOrder(@PathVariable UUID orderId) {
        OrderDetailResDto orderDetailResDto = orderService.getOrder(orderId);

        ResponseDto responseDto = new ResponseDto<>(HttpStatus.OK.value(), "get order successfully", orderDetailResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseDto> updateOrder(@PathVariable UUID orderId, @RequestBody OrderUpdateReqDto orderUpdateReqDto) {
        OrderUpdateResDto orderUpdateResDto = orderService.updateOrder(orderId, orderUpdateReqDto);

        ResponseDto responseDto = new ResponseDto<>(HttpStatus.OK.value(), "update order successfully", orderUpdateResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ResponseDto> cancelOrderStatus(@PathVariable UUID orderId) {
        OrderUpdateResDto orderUpdateResDto = orderService.cancelOrder(orderId, ORDER_CANCELLED);

        ResponseDto responseDto = new ResponseDto<>(HttpStatus.OK.value(), "cancel order successfully", orderUpdateResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

}
