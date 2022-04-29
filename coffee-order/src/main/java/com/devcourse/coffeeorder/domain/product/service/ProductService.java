package com.devcourse.coffeeorder.domain.product.service;

import com.devcourse.coffeeorder.domain.product.dao.ProductRepository;
import com.devcourse.coffeeorder.domain.product.dto.ProductCreateReqDto;
import com.devcourse.coffeeorder.domain.product.dto.ProductCreateResDto;
import com.devcourse.coffeeorder.domain.product.dto.ProductResDto;
import com.devcourse.coffeeorder.domain.product.dto.ProductsResDto;
import com.devcourse.coffeeorder.domain.product.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 생성
    public ProductCreateResDto createProduct(ProductCreateReqDto productCreateReqDto) {
        Product newProduct = productRepository.create(productCreateReqDto.toEntity());
        return new ProductCreateResDto(newProduct.getProductId(),
                newProduct.getProductName(),
                newProduct.getCreatedAt());
    }

    // 전체 조회
    public ProductsResDto findAll() {
        List<ProductResDto> products =  productRepository.findAll().stream().map(product -> ProductResDto.builder()
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

    // ID에 의한 조회

    // update

    // delete
}
