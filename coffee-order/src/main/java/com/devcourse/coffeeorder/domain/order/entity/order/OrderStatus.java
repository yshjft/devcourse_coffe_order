package com.devcourse.coffeeorder.domain.order.entity.order;

public enum OrderStatus {
    ORDER_ACCEPTED("order-accepted", "접수 완료"),
    PREPARING_FOR_SHIPMENT("preparing-for-shipment", "배송 준비"),
    ON_DELIVERY("on-delivery", "배송 중"),
    DELIVERY_OVER("delivery-over", "배송 완료"),
    ORDER_CANCELLED("order-cancelled", "주문 취소");

    private final String statusName;
    private final String korName;

    OrderStatus(String statusName, String korName) {
        this.statusName = statusName;
        this.korName = korName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getKorName() {
        return korName;
    }
}
