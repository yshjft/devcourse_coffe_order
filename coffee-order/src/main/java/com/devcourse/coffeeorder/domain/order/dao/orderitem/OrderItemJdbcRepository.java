package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import static com.devcourse.coffeeorder.global.util.Util.toLocalDateTime;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.global.exception.CreationException;
import com.devcourse.coffeeorder.global.util.Util;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemJdbcRepository implements OrderItemRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderItemJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        int update = jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, quantity, created_at, updated_at) " +
                "VALUES(UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :quantity, :createdAt, :updatedAt)", toOrderItemParamMap(orderItem));

        if(update != 1) {
            throw new CreationException("failed to create orderItem!");
        }

        return orderItem;
    }

    @Override
    public List<OrderItem> findAll() {
        return jdbcTemplate.query("SELECT * FROM order_items", orderItemRowMapper);
    }

    private final RowMapper<OrderItem> orderItemRowMapper = ((resultSet, i) -> {
        Long orderItemId = resultSet.getLong("seq");
        UUID orderId = Util.toUUID(resultSet.getBytes("order_id"));
        UUID productId = Util.toUUID(resultSet.getBytes("product_id"));
        int quantity = resultSet.getInt("quantity");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return OrderItem.builder()
                .orderItemId(orderItemId)
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    });

    private Map<String, Object> toOrderItemParamMap(OrderItem orderItem) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderItem.getOrderId().toString().getBytes());
        paramMap.put("productId", orderItem.getProductId().toString().getBytes());
        paramMap.put("quantity", orderItem.getQuantity());
        paramMap.put("createdAt", orderItem.getCreatedAt());
        paramMap.put("updatedAt", orderItem.getUpdatedAt());
        return paramMap;
    }
}
