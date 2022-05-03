package com.devcourse.coffeeorder.domain.product.controller;

import com.devcourse.coffeeorder.domain.product.dto.ProductsResDto;
import com.devcourse.coffeeorder.domain.product.service.ProductService;
import com.devcourse.coffeeorder.global.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 목록 조회
     **/
    @GetMapping
    public ResponseEntity<ResponseDto> getProducts() {
        ProductsResDto productsResDto = productService.findAllProducts();

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "get products successfully", productsResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
