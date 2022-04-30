package com.devcourse.coffeeorder.domain.product.service;

import com.devcourse.coffeeorder.domain.product.dao.ProductRepository;
import com.devcourse.coffeeorder.domain.product.dto.*;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 생성
    public ProductCreateResDto createProduct(ProductReqDto productReqDto) {
        Product newProduct = productRepository.create(productReqDto.toEntity());
        return new ProductCreateResDto(newProduct.getProductId(),
                newProduct.getProductName(),
                newProduct.getCreatedAt());
    }

    // 전체 조회
    public ProductsResDto findAll() {
        List<ProductResDto> products = productRepository.findAll().stream()
                .map(product -> ProductResDto.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .createdAt(product.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return new ProductsResDto(products.size(), products);
    }

    // 카테고리 조회
    public ProductsResDto findAllWithCategory(Category category) {
        List<ProductResDto> products = productRepository.findByCategory(category).stream()
                .map(product -> ProductResDto.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .createdAt(product.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return new ProductsResDto(products.size(), products);
    }

    // ID에 의한 조회
    public ProductDetailResDto findByProductId(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        return ProductDetailResDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .category(product.getCategory())
                .price(product.getPrice())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    // update
    public ProductUpdateResDto updateProduct(UUID productId, ProductReqDto productReqDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        product.updateProduct(productReqDto.getProductName(),
                productReqDto.getCategory(),
                productReqDto.getPrice(),
                productReqDto.getDescription());

        Product updatedProduct = productRepository.update(product);

        return new ProductUpdateResDto(updatedProduct.getProductId(),
                updatedProduct.getProductName(),
                updatedProduct.getUpdatedAt());
    }

    // delete
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));
        productRepository.delete(product);
    }
}
