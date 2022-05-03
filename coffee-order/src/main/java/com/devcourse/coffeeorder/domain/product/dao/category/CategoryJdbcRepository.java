package com.devcourse.coffeeorder.domain.product.dao.category;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.global.exception.customexception.UpdateException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.devcourse.coffeeorder.global.util.Util.toLocalDateTime;

@Repository
public class CategoryJdbcRepository implements CategoryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category create(Category category) {
        int update = jdbcTemplate.update("INSERT INTO categories(category, created_at) VALUES(:category, :createdAt)", toParamMap(category));

        if(update != 1) {
            throw new UpdateException("failed to create category");
        }

        return category;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM categories", categoryRowMapper);
    }

    @Override
    public Optional<Category> findByCategory(String category) {
        try {
            return Optional.of(
                jdbcTemplate.queryForObject("SELECT * FROM categories WHERE category = :category",
                        Collections.singletonMap("category", category), categoryRowMapper )
            );
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Category category) {
        jdbcTemplate.update("DELETE FROM categories WHERE category = :category",
                Collections.singletonMap("category", category.getCategory()));
    }

    private final RowMapper<Category> categoryRowMapper = ((resultSet, i) -> {
        String category = resultSet.getString("category");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        return new Category(category, createdAt);
    });

    private static final Map<String, Object> toParamMap(Category category) {
        Map<String, Object> paramMap  = new HashMap();
        paramMap.put("category", category.getCategory());
        paramMap.put("createdAt", category.getCreatedAt());
        return paramMap;
    }
}
