package com.devcourse.coffeeorder.domain.order.dao.orderitem;

import static com.devcourse.coffeeorder.global.util.Util.toLocalDateTime;

import java.time.LocalDateTime;
import java.util.*;

import com.devcourse.coffeeorder.domain.order.entity.order.Order;
import com.devcourse.coffeeorder.domain.order.entity.order.OrderStatus;
import com.devcourse.coffeeorder.domain.order.entity.orderitem.OrderItem;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.CreationException;
import com.devcourse.coffeeorder.global.exception.UpdateException;
import com.devcourse.coffeeorder.global.util.Util;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public List<OrderItem> findByOrderIdWithProduct(UUID orderId) {
        return jdbcTemplate.query("SELECT * FROM order_items JOIN products ON order_items.product_id = products.product_id WHERE order_items.order_id = UUID_TO_BIN(:orderId) ORDER BY order_items.seq",
                Collections.singletonMap("orderId", orderId.toString().getBytes()),
                orderItemWithProductRowMapper);
    }

    @Override
    public Optional<OrderItem> findByOrderItemIdWithOrder(Long orderItemId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM order_items JOIN orders ON order_items.order_id = orders.order_id WHERE order_items.seq = :orderItemId",
                            Collections.singletonMap("orderItemId", orderItemId), orderItemWithOrderRowMapper)
            );
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        int update = jdbcTemplate.update("UPDATE order_items SET quantity = :quantity, updated_at = :updatedAt WHERE seq = :orderItemId", toOrderItemParamMap(orderItem));

        if(update != 1) {
            throw new UpdateException("failed to update orderItem");
        }

        return orderItem;
    }

    @Override
    public void delete(OrderItem orderItem) {
        jdbcTemplate.update("DELETE FROM order_items WHERE seq = :orderItemId", toOrderItemParamMap(orderItem));
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

    private final RowMapper<OrderItem> orderItemWithProductRowMapper = ((resultSet, i) -> {
        Long orderItemId = resultSet.getLong("order_items.seq");
        UUID orderId = Util.toUUID(resultSet.getBytes("order_items.order_id"));
        UUID productId = Util.toUUID(resultSet.getBytes("order_items.product_id"));
        int quantity = resultSet.getInt("order_items.quantity");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("order_items.created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("order_items.updated_at"));

        String productName = resultSet.getString("products.product_name");
        Category category = Category.valueOf(resultSet.getString("products.category"));
        long price = resultSet.getLong("products.price");
        String description = resultSet.getString("products.description");
        LocalDateTime productCreatedAt = toLocalDateTime(resultSet.getTimestamp("products.created_at"));
        LocalDateTime productUpdatedAt = toLocalDateTime(resultSet.getTimestamp("products.updated_at"));

        Product product = Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .createdAt(productCreatedAt)
                .updatedAt(productUpdatedAt)
                .build();

        return OrderItem.builder()
                .orderItemId(orderItemId)
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .product(product)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    });

    private final RowMapper<OrderItem> orderItemWithOrderRowMapper = ((resultSet, i) -> {
        Long orderItemId = resultSet.getLong("order_items.seq");
        UUID orderId = Util.toUUID(resultSet.getBytes("order_items.order_id"));
        UUID productId = Util.toUUID(resultSet.getBytes("order_items.product_id"));
        int quantity = resultSet.getInt("order_items.quantity");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("order_items.created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("order_items.updated_at"));

        String email = resultSet.getString("orders.email");
        String address = resultSet.getString("orders.address");
        String postcode = resultSet.getString("orders.postcode");
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("orders.order_status"));
        LocalDateTime orderCreatedAt = toLocalDateTime(resultSet.getTimestamp("orders.created_at"));
        LocalDateTime orderUpdatedAt = toLocalDateTime(resultSet.getTimestamp("orders.updated_at"));

        Order order = Order.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(orderStatus)
                .createdAt(orderCreatedAt)
                .updatedAt(orderUpdatedAt)
                .build();

        return OrderItem.builder()
                .orderItemId(orderItemId)
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .order(order)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    });

    private Map<String, Object> toOrderItemParamMap(OrderItem orderItem) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("orderItemId", orderItem.getOrderItemId());
        paramMap.put("orderId", orderItem.getOrderId().toString().getBytes());
        paramMap.put("productId", orderItem.getProductId().toString().getBytes());
        paramMap.put("quantity", orderItem.getQuantity());
        paramMap.put("createdAt", orderItem.getCreatedAt());
        paramMap.put("updatedAt", orderItem.getUpdatedAt());
        return paramMap;
    }
}
