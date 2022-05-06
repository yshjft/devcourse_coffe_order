package com.devcourse.coffeeorder.domain.product.service;

import java.util.List;
import java.util.stream.Collectors;

import com.devcourse.coffeeorder.domain.product.dao.category.CategoryRepository;
import com.devcourse.coffeeorder.domain.product.dao.product.ProductRepository;
import com.devcourse.coffeeorder.domain.product.dto.category.CategoriesResDto;
import com.devcourse.coffeeorder.domain.product.dto.category.CategoryDetailResDto;
import com.devcourse.coffeeorder.domain.product.dto.category.CategoryDto;
import com.devcourse.coffeeorder.domain.product.dto.category.CategoryCreateResDto;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.global.common.MetaData;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.CategoryException;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * 카테고리 생성
     */
    public CategoryCreateResDto createCategory(CategoryDto categoryDto) {
        if(categoryRepository.findByCategory(categoryDto.getCategory()).isPresent()) {
            throw new CategoryException(String.format("%s is duplicate category!", categoryDto.getCategory()));
        }

        Category createdCategory = categoryRepository.create(categoryDto.toEntity());

        return new CategoryCreateResDto(
                categoryDto.getCategory(),
                createdCategory.getCreatedAt());
    }

    /**
     * 카테고리 목록 조회
     */
    public CategoriesResDto getCategories() {
        List<CategoryDto> categories = categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getCategory()))
                .collect(Collectors.toList());

        MetaData metaData = new MetaData(categories.size());

        return new CategoriesResDto(metaData, categories);
    }

    /**
     * 카테고리 상세 조회
     */
    public CategoryDetailResDto getCategory(String categoryType) {
        Category category = categoryRepository.findByCategory(categoryType).orElseThrow(() -> new CategoryException(categoryType));

        return new CategoryDetailResDto(
                category.getCategory(),
                category.getCreatedAt());
    }

    /**
     * 카테고리 삭제
     */
    public void deleteCategory(String categoryType) {
        Category category = categoryRepository.findByCategory(categoryType).orElseThrow(() -> new CategoryException(categoryType));

        if(productRepository.findByCategory(categoryType).size() > 0) {
            throw new CategoryException(String.format("can't delete %s! because it's used by product.", categoryType));
        }

        categoryRepository.delete(category);
    }

}
