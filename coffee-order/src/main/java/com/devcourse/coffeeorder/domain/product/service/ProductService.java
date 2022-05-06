package com.devcourse.coffeeorder.domain.product.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.devcourse.coffeeorder.domain.order.dao.orderitem.OrderItemRepository;
import com.devcourse.coffeeorder.domain.product.dao.category.CategoryRepository;
import com.devcourse.coffeeorder.domain.product.dao.product.ProductRepository;
import com.devcourse.coffeeorder.domain.product.dto.product.*;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import com.devcourse.coffeeorder.global.common.MetaData;
import com.devcourse.coffeeorder.global.exception.customexception.badrequest.ProductException;
import com.devcourse.coffeeorder.global.exception.customexception.notfound.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, OrderItemRepository orderItemRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 상품 생성
     **/
    public ProductCreateResDto createProduct(ProductReqDto productReqDto) {
        if(categoryRepository.findByCategory(productReqDto.getCategory()).isEmpty()) {
            throw new ProductException(String.format("%s is wrong category", productReqDto.getCategory()));
        }

        Product newProduct = productRepository.create(productReqDto.toEntity());

        return new ProductCreateResDto(newProduct.getProductId(),
                newProduct.getProductName(),
                newProduct.getCreatedAt());
    }

    /**
     * 상품 목록 조회
     **/
    public ProductsResDto findAllProducts() {
        List<ProductResDto> productList = productRepository.findAll().stream()
                .map(product -> ProductResDto.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .createdAt(product.getCreatedAt())
                        .updatedAt(product.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
        MetaData metaData = new MetaData(productList.size());

        return new ProductsResDto(metaData, productList);
    }

    /**
     * 카테고리별 상품 목록 조회
     **/
    public ProductsResDto findAllProductsByCategory(String category) {
        List<ProductResDto> productList = productRepository.findByCategory(category).stream()
                .map(product -> ProductResDto.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .createdAt(product.getCreatedAt())
                        .updatedAt(product.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        MetaData metaData = new MetaData(productList.size());

        return new ProductsResDto(metaData, productList);
    }

    /**
     * ID를 통한 상품 상세 조회
     **/
    public ProductResDto findProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        return ProductResDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .category(product.getCategory())
                .price(product.getPrice())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    /**
     * 상품 수정
     **/
    public ProductUpdateResDto updateProduct(UUID productId, ProductReqDto productReqDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        if(categoryRepository.findByCategory(productReqDto.getCategory()).isEmpty()) {
            throw new ProductException(String.format("%s is wrong category", productReqDto.getCategory()));
        }

        product.updateProduct(productReqDto.getProductName(),
                productReqDto.getCategory(),
                productReqDto.getPrice(),
                productReqDto.getDescription());

        Product updatedProduct = productRepository.update(product);

        return new ProductUpdateResDto(updatedProduct.getProductId(),
                updatedProduct.getProductName(),
                updatedProduct.getUpdatedAt());
    }

    /**
     * 상품 삭제
     */
    public UUID deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        if(orderItemRepository.findByProductId(productId).size() > 0) {
            throw new ProductException(String.format("can't delete product %s! because it's used by orderItem.", productId.toString()));
        }

        productRepository.delete(product);

        return productId;
    }
}
