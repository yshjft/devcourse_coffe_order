package com.devcourse.coffeeorder.domain.orderitem.controller;

import com.devcourse.coffeeorder.domain.orderitem.dto.OrderItemUpdateReqDto;
import com.devcourse.coffeeorder.domain.orderitem.dto.OrderItemUpdateResDto;
import com.devcourse.coffeeorder.domain.orderitem.service.OrderItemService;
import com.devcourse.coffeeorder.global.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-items")
public class OrderItemRestController {
    private final OrderItemService orderItemService;

    public OrderItemRestController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<ResponseDto> updateQuantity(@PathVariable Long orderItemId, @RequestBody OrderItemUpdateReqDto orderItemUpdateReqDto) {
        OrderItemUpdateResDto orderItemUpdateResDto = orderItemService.updateOrderItemQuantity(orderItemId, orderItemUpdateReqDto.getQuantity());

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(),
                "update orderItem quantity successfully!",
                orderItemUpdateResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "delete orderItem successfully!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
