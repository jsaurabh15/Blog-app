package com.application.blog.controllers;

import com.application.blog.exceptions.CategoryAlreadyExistsException;
import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.CategoryWrapper;
import com.application.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Create Category
    @PostMapping("/create_category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryWrapper categoryWrapper) {
        try {
            CategoryWrapper createdCategoryWrapper = this.categoryService.createCategory(categoryWrapper);
            return new ResponseEntity<>(createdCategoryWrapper, HttpStatus.CREATED);
        } catch (CategoryAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    //Update Category
    @PutMapping("/update_category/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryWrapper> updateCategory(@Valid @RequestBody CategoryWrapper categoryWrapper, @PathVariable Integer categoryId) {
        CategoryWrapper updatedCategoryWrapper = this.categoryService.updateCategory(categoryWrapper, categoryId);
        return new ResponseEntity<>(updatedCategoryWrapper, HttpStatus.OK);
    }

    //Delete Category
    @DeleteMapping("/delete_category/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }

    //Get Category
    @GetMapping("/get_category/{categoryId}")
    public ResponseEntity<CategoryWrapper> getCategoryById(@PathVariable Integer categoryId) {
        CategoryWrapper categoryWrapper = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryWrapper, HttpStatus.OK);
    }

    //Get All Categories
    @GetMapping("get_categories")
    public ResponseEntity<List<CategoryWrapper>> getAllCategories() {
        List<CategoryWrapper> allCategories = this.categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

}
