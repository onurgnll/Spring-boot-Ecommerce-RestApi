package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.AddToCategoryRequest;
import com.ecommerce.app.requests.CreateCategoryRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategories(){
        return ResponseHandler.generateResponse(200, categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<Object> createCategort(@RequestBody CreateCategoryRequest createCategoryRequest){
        return ResponseHandler.generateResponse(200, categoryService.createCategory(createCategoryRequest));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addProductToCategory(@RequestBody AddToCategoryRequest addToCategoryRequest){
        return ResponseHandler.generateResponse(200, categoryService.addProductToCategory(addToCategoryRequest.getCategoryName(), addToCategoryRequest.getProductId()));
    }

}
