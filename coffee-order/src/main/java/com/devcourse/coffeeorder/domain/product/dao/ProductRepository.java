package com.devcourse.coffeeorder.domain.product.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;

public interface ProductRepository {
    // 생성
    Product create(Product product);

    // 전체 조회
    List<Product> findAll();

    // 카테고리로 조회
    List<Product> findByCategory(Category category);

    // id로 조회
    Optional<Product> findById(UUID productId);

    // 업데이트
    Product update(Product product);

    // 삭제
    void delete(Product product);
}
