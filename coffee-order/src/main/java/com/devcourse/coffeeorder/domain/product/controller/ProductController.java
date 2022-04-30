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

    // CREATE
    @GetMapping("/products/new")
    public String viewNewProductsPage(Model model) {
        model.addAttribute("categories", Category.values());
        return "product/new-product";
    }

    @PostMapping("/products/new")
    public String addProduct(@ModelAttribute ProductReqDto productReqDto) {
        productService.createProduct(productReqDto);
        return "redirect:/products";
    }

    // READ
    @GetMapping("/products")
    public String viewProductsPage(@RequestParam(required = false) Category category, Model model) {
        ProductsResDto productsResDto = category == null ?
                productService.findAll() :
                productService.findAllWithCategory(category);

        model.addAttribute("categories", Category.values());
        model.addAttribute("products", productsResDto.getProducts());

        return "product/products";
    }

    @GetMapping("/products/{productId}")
    public String viewProductPage(@PathVariable UUID productId, Model model) {
        ProductResDto productResDto = productService.findByProductId(productId);
        model.addAttribute("product", productResDto);

        return "product/product";
    }

    // UPDATE
    @GetMapping("/products/{productId}/update")
    public String viewProductUpdatePage(@PathVariable UUID productId, Model model) {
        ProductResDto productResDto = productService.findByProductId(productId);

        model.addAttribute("categories", Category.values());
        model.addAttribute("product", productResDto);
        return "product/update-product";
    }

    @PostMapping("/products/update/{productId}")
    public String updateProduct(@PathVariable UUID productId, @ModelAttribute ProductReqDto productReqDto) {
        productService.updateProduct(productId, productReqDto);
        return "redirect:/products/"+productId;
    }

    // DELETE
    @PostMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
}
