package com.devcourse.coffeeorder.domain.product.controller;

import com.devcourse.coffeeorder.domain.product.dto.ProductCreateReqDto;
import com.devcourse.coffeeorder.domain.product.dto.ProductsResDto;
import com.devcourse.coffeeorder.domain.product.entity.Category;
import com.devcourse.coffeeorder.domain.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String viewProductsPage(Model model) {
        ProductsResDto productsResDto = productService.findAll();

        model.addAttribute("products", productsResDto.getProducts());

        return "product/products";
    }

    @GetMapping("/products/new")
    public String viewNewProductsPage(Model model) {
        model.addAttribute("categories", Category.values());
        return "product/new-product";
    }

    @PostMapping("/products/new")
    public String addProduct(@ModelAttribute ProductCreateReqDto productCreateReqDto) {
        productService.createProduct(productCreateReqDto);
        return "redirect:/products";
    }
}
