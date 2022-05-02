package com.devcourse.coffeeorder.domain.orderItem.controller;

import com.devcourse.coffeeorder.domain.orderItem.dto.OrderItemUpdateReqDto;
import com.devcourse.coffeeorder.domain.orderItem.dto.OrderItemUpdateResDto;
import com.devcourse.coffeeorder.domain.orderItem.service.OrderItemService;
import com.devcourse.coffeeorder.global.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemRestController {
    private final OrderItemService orderItemService;

    public OrderItemRestController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<ResponseDto> updateQuantity(@PathVariable Long orderItemId, @RequestBody OrderItemUpdateReqDto orderItemUpdateReqDto) {
        OrderItemUpdateResDto orderItemUpdateResDto = orderItemService.updateOrderItem(orderItemId, orderItemUpdateReqDto.getQuantity());

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(),
                "update orderItem quantity successfully!",
                orderItemUpdateResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
