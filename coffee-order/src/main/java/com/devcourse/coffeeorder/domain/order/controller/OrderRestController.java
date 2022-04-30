package com.devcourse.coffeeorder.domain.order.controller;

import com.devcourse.coffeeorder.domain.order.dto.order.OrderCreateReqDto;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderCreateResDto;
import com.devcourse.coffeeorder.domain.order.service.OrderService;
import com.devcourse.coffeeorder.global.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        ResponseDto responseDto = new ResponseDto<>(
                HttpStatus.CREATED.value(),
                "create order successfully",
                orderCreateResDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }
}
