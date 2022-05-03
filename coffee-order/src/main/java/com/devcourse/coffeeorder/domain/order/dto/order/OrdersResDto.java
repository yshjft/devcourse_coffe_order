package com.devcourse.coffeeorder.domain.order.dto.order;

import com.devcourse.coffeeorder.global.common.MetaData;

import java.util.List;

public class OrdersResDto {
    private MetaData metaData;
    private List<OrderResDto> orders;

    public OrdersResDto(MetaData metaData, List<OrderResDto> orders) {
        this.metaData = metaData;
        this.orders = orders;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<OrderResDto> getOrders() {
        return orders;
    }
}
