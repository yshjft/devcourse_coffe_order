package com.devcourse.coffeeorder.domain.order.controller.order;

import java.util.List;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.dto.order.OrderDetailResDto;
import com.devcourse.coffeeorder.domain.order.dto.order.OrderResDto;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 주문 조회 페이지
     */
    @GetMapping("/orders")
    public String viewOrdersPage(Model model) {
        List<OrderResDto> acceptedOrders = orderService.getOrdersByStatus(OrderStatus.ORDER_ACCEPTED).getOrders();
        List<OrderResDto> preparingOrders = orderService.getOrdersByStatus(OrderStatus.PREPARING_FOR_SHIPMENT).getOrders();
        List<OrderResDto> onDeliveryOrders = orderService.getOrdersByStatus(OrderStatus.ON_DELIVERY).getOrders();
        List<OrderResDto> deliveryOverOrders = orderService.getOrdersByStatus(OrderStatus.DELIVERY_OVER).getOrders();
        List<OrderResDto> cancelledOrders = orderService.getOrdersByStatus(OrderStatus.ORDER_CANCELLED).getOrders();

        model.addAttribute("acceptedOrders", acceptedOrders);
        model.addAttribute("preparingOrders", preparingOrders);
        model.addAttribute("onDeliveryOrders", onDeliveryOrders);
        model.addAttribute("deliveryOverOrders", deliveryOverOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        return"order/orders";
    }

    /**
     * 주문 상세 조회 페이지
     */
    @GetMapping("/orders/{orderId}")
    public String viewOrderPage(@PathVariable UUID orderId, Model model) {
        OrderDetailResDto orderDetailResDto = orderService.getOrder(orderId);

        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("order", orderDetailResDto);

        return "order/order";
    }

    /**
     * 주문 상태 수정
     */
    @PostMapping("/orders/status/update/{orderId}")
    public String updateOrderStatus(@PathVariable UUID orderId, @RequestParam OrderStatus orderStatus) {
        orderService.updateOrderStatus(orderId, orderStatus);

        return "redirect:/orders/"+orderId;
    }
}
