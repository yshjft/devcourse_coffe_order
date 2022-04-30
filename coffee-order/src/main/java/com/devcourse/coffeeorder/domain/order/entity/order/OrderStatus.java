package com.devcourse.coffeeorder.domain.order.entity.order;

public enum OrderStatus {
    ORDER_ACCEPTED("order-accepted"),
    PACKING_FOR_SHIPMENT("packing-for-shipment"),
    ON_DELIVERY("on-delivery"),
    DELIVERY_OVER("deliver-over"),
    ORDER_CANCELLED("order-cancelled");

    private final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
