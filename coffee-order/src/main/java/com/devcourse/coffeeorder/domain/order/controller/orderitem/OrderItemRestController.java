package com.devcourse.coffeeorder.domain.order.controller.orderitem;

import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemUpdateReqDto;
import com.devcourse.coffeeorder.domain.order.dto.orderitem.OrderItemUpdateResDto;
import com.devcourse.coffeeorder.domain.order.service.OrderItemService;
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

    /**
     * 주문 상품 수량 수정
     */
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

    /**
     * 주문 상품 삭제
     */
    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<ResponseDto> deleteOrderItem(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "delete orderItem successfully!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
