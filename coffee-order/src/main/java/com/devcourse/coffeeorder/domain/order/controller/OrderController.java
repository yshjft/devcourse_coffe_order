package com.devcourse.coffeeorder.domain.order.controller;

import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.dto.order.OrderDetailResDto;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderResDto;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.service.OrderService;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String viewOrdersPage(Model model) {
        List<OrderResDto> acceptedOrders = orderService.getOrderByStatus(OrderStatus.ORDER_ACCEPTED);
        List<OrderResDto> preparingOrders = orderService.getOrderByStatus(OrderStatus.PREPARING_FOR_SHIPMENT);
        List<OrderResDto> onDeliveryOrders = orderService.getOrderByStatus(OrderStatus.ON_DELIVERY);
        List<OrderResDto> deliveryOverOrders = orderService.getOrderByStatus(OrderStatus.DELIVERY_OVER);
        List<OrderResDto> cancelledOrders = orderService.getOrderByStatus(OrderStatus.ORDER_CANCELLED);

        model.addAttribute("acceptedOrders", acceptedOrders);
        model.addAttribute("preparingOrders", preparingOrders);
        model.addAttribute("onDeliveryOrders", onDeliveryOrders);
        model.addAttribute("deliveryOverOrders", deliveryOverOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        return"order/orders";
    }

    @GetMapping("/orders/{orderId}")
    public String viewOrderPage(@PathVariable UUID orderId, Model model) {
        OrderDetailResDto orderDetailResDto = orderService.getOrderDetail(orderId);

        model.addAttribute("categories", OrderStatus.values());
        model.addAttribute("order", orderDetailResDto);

        return "order/order";
    }
}
