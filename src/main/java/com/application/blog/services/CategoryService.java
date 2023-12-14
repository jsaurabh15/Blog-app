package com.application.blog.services;

import com.application.blog.payloads.CategoryWrapper;

import java.util.List;

public interface CategoryService {

   CategoryWrapper createCategory(CategoryWrapper categoryWrapper);
    CategoryWrapper updateCategory(CategoryWrapper categoryWrapper, Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryWrapper getCategoryById(Integer categoryId);
    List<CategoryWrapper> getAllCategories();
}
