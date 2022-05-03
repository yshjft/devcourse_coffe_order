package com.devcourse.coffeeorder.domain.product.controller;

import java.util.UUID;

import com.devcourse.coffeeorder.domain.product.dto.ProductReqDto;
import com.devcourse.coffeeorder.domain.product.dto.ProductResDto;
import com.devcourse.coffeeorder.domain.product.dto.ProductsResDto;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 생성 페이지
    @GetMapping("/products/new")
    public String viewNewProductsPage(Model model) {
        model.addAttribute("categories", Category.values());
        return "product/new-product";
    }

    // 상품 생성
    @PostMapping("/products/new")
    public String addProduct(@ModelAttribute ProductReqDto productReqDto) {
        productService.createProduct(productReqDto);
        return "redirect:/products";
    }

    // 상품 목록 조회 페이지
    @GetMapping("/products")
    public String viewProductsPage(@RequestParam(required = false) Category category, Model model) {
        ProductsResDto productsResDto = category == null ?
                productService.findAllProducts() :
                productService.findAllProductsByCategory(category);

        model.addAttribute("categories", Category.values());
        model.addAttribute("products", productsResDto.getProducts());

        return "product/products";
    }

    // 상품 상제 조회 페이지
    @GetMapping("/products/{productId}")
    public String viewProductPage(@PathVariable UUID productId, Model model) {
        ProductResDto productResDto = productService.findProduct(productId);
        model.addAttribute("product", productResDto);

        return "product/product";
    }

    // 상품 수정 페이지
    @GetMapping("/products/{productId}/update")
    public String viewProductUpdatePage(@PathVariable UUID productId, Model model) {
        ProductResDto productResDto = productService.findProduct(productId);

        model.addAttribute("categories", Category.values());
        model.addAttribute("product", productResDto);
        return "product/update-product";
    }

    // 상품 수정
    @PostMapping("/products/update/{productId}")
    public String updateProduct(@PathVariable UUID productId, @ModelAttribute ProductReqDto productReqDto) {
        productService.updateProduct(productId, productReqDto);
        return "redirect:/products/"+productId;
    }

    // 상품 삭제
    @PostMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
}
