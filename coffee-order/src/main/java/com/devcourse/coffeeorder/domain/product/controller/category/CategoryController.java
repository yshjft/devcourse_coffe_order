package com.devcourse.coffeeorder.domain.product.controller.category;

import java.util.List;

import com.devcourse.coffeeorder.domain.product.dto.category.CategoryDetailResDto;
import com.devcourse.coffeeorder.domain.product.dto.category.CategoryDto;
import com.devcourse.coffeeorder.domain.product.dto.product.ProductResDto;
import com.devcourse.coffeeorder.domain.product.service.CategoryService;
import com.devcourse.coffeeorder.domain.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/error";
    }

    /**
     * 카테고리 생성 페이지
     */
    @GetMapping("/categories/new")
    public String viewNewCategoryPage(Model model) {
        return "category/new-category";
    }

    /**
     * 카테고리 생성
     */
    @PostMapping("/categories/new")
    public String viewNewCategoryPage(@ModelAttribute CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return "redirect:/categories";
    }

    /**
     * 카테고리 조회 페이지
     */
    @GetMapping("/categories")
    public String viewCategoriesPage(Model model) {
        List<CategoryDto> categories = categoryService.getCategories().getCategories();

        model.addAttribute("categories", categories);

        return "category/categories";
    }

    /**
     * 카테고리 상세 조회 페이지
     */
    @GetMapping("/categories/{categoryType}")
    public String viewCategoryPage(@PathVariable String categoryType, Model model) {
        CategoryDetailResDto category = categoryService.getCategory(categoryType);
        List<ProductResDto> products = productService.findAllProductsByCategory(categoryType).getProducts();

        model.addAttribute("category", category);
        model.addAttribute("products", products);

        return "category/category";
    }

    @PostMapping("/categories/delete/{categoryType}")
    public String viewCategoryPage(@PathVariable String categoryType) {
        categoryService.delete(categoryType);

        return "redirect:/categories";
    }
}
