package com.devcourse.coffeeorder.domain.product.dao.category;

import java.util.List;
import java.util.Optional;

import com.devcourse.coffeeorder.domain.product.entity.Category;

public interface CategoryRepository {
    /**
     * 생성
     */
    Category create(Category category);

    /**
     * 조회
     */
    List<Category> findAll();

    /**
     * 상세 조회
     */
    Optional<Category> findByCategory(String category);

    /**
     * 삭제
     */
    void delete(Category category);
}
