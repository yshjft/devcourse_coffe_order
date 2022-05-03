package com.devcourse.coffeeorder.domain.product.dao.product;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.CreationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.devcourse.coffeeorder.global.util.Util.toLocalDateTime;
import static com.devcourse.coffeeorder.global.util.Util.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product create(Product product) {
        int update = jdbcTemplate.update("INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at) " +
                "VALUES(UUID_TO_BIN(:productId), :productName, :category, :price, :description, :createdAt, :updatedAt)", toParamMap(product));

        if(update != 1) {
            throw new CreationException("failed to create product!");
        }

        return product;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return jdbcTemplate.query("SELECT * FROM products WHERE category = :category",
                Collections.singletonMap("category", category), productRowMapper);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = UUID_TO_BIN(:productId)",
                            Collections.singletonMap("productId", productId.toString().getBytes()) ,productRowMapper)
            );
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Product update(Product product) {
        int update = jdbcTemplate.update("UPDATE products SET product_name=:productName, category=:category, price=:price, description=:description, created_at=:createdAt, updated_at=:updatedAt" +
                        " where product_id = UUID_TO_BIN(:productId)",
                toParamMap(product));

        if(update != 1) {
            throw new CreationException("failed to update product!");
        }

        return product;
    }

    @Override
    public void delete(Product product) {
        jdbcTemplate.update("DELETE FROM products WHERE product_id = UUID_TO_BIN(:productId)",
                Collections.singletonMap("productId", product.getProductId().toString().getBytes()));
    }

    private final RowMapper<Product> productRowMapper = ((resultSet, i) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        String productName = resultSet.getString("product_name");
        String category = resultSet.getString("category");
        long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    });

    private static final Map<String, Object> toParamMap(Product product) {
        Map<String, Object> paramMap  = new HashMap();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());
        return paramMap;
    }
}
