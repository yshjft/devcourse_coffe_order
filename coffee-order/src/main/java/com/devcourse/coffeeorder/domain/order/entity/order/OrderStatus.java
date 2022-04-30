package com.devcourse.coffeeorder.domain.order.entity.order;

public enum OrderStatus {
    ORDER_ACCEPTED("order-accepted"),
    PREPARING_FOR_SHIPMENT("preparing-for-shipment"),
    ON_DELIVERY("on-delivery"),
    DELIVERY_OVER("delivery-over"),
    ORDER_CANCELLED("order-cancelled");

    private final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
