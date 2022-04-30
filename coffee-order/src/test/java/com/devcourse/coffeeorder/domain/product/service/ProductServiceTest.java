package com.devcourse.coffeeorder.domain.product.service;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.devcourse.coffeeorder.domain.product.dao.ProductRepository;
import com.devcourse.coffeeorder.domain.product.dto.ProductReqDto;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    Product product = Product.builder()
            .productId(UUID.randomUUID())
            .productName("test coffee")
            .category(Category.COFFEE_BEAN_PACKAGE)
            .price(2500)
            .description("best coffee!")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    ProductReqDto productReqDto = ProductReqDto.builder()
            .productName("test")
            .category(Category.COFFEE_BEAN_PACKAGE)
            .price(1000)
            .description("커피 커피")
            .build();

    @Test
    @DisplayName("삭제 예외 행위 테스트")
    void testDeleteByProductIdException() {
        try {
            UUID productId = UUID.randomUUID();
            when(productRepository.findById(productId)).thenThrow(new ProductNotFoundException(productId.toString()));

            productService.deleteProduct(productId);
        }catch (ProductNotFoundException e) {
            verify(productRepository, never()).delete(any());
        }
    }

    @Test
    @DisplayName("삭제 행위 테스트")
    void testDeleteByProductId() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        productService.deleteProduct(product.getProductId());

        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("수정 예외 행위 테스트")
    void testUpdateException() {
        try{
            UUID productId = UUID.randomUUID();
            when(productRepository.findById(productId)).thenThrow(new ProductNotFoundException(productId.toString()));

            productService.updateProduct(productId, productReqDto);
        }catch(ProductNotFoundException e) {
            verify(productRepository, never()).update(any());
        }
    }

    @Test
    @DisplayName("수정 행위 테스트")
    void testUpdate() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(productRepository.update(product)).thenReturn(product);

        productService.updateProduct(product.getProductId(), productReqDto);

        verify(productRepository).update(product);
    }
}