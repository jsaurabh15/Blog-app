package com.application.blog.services.impl;

import com.application.blog.dao.CategoryDao;
import com.application.blog.exceptions.CategoryAlreadyExistsException;
import com.application.blog.exceptions.ResourceNotFoundException;
import com.application.blog.models.Category;
import com.application.blog.payloads.CategoryWrapper;
import com.application.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryWrapper createCategory(CategoryWrapper categoryWrapper) {
        Category category = this.modelMapper.map(categoryWrapper, Category.class);
        if(this.categoryDao.existsByCategoryTitle(category.getCategoryTitle())) {
            throw  new CategoryAlreadyExistsException(category.getCategoryTitle() + " already exists!");
        }
        Category savedCategory = this.categoryDao.save(category);
        return this.modelMapper.map(savedCategory, CategoryWrapper.class);
    }

    @Override
    public CategoryWrapper updateCategory(CategoryWrapper categoryWrapper, Integer categoryId) {
        Category category = this.categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));

        category.setCategoryTitle(categoryWrapper.getCategoryTitle());
        category.setCategoryDescription(categoryWrapper.getCategoryDescription());
        Category updatedCategory = this.categoryDao.save(category);

        return this.modelMapper.map(updatedCategory, CategoryWrapper.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));

        this.categoryDao.delete(category);
    }

    @Override
    public CategoryWrapper getCategoryById(Integer categoryId) {

        Category category = this.categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));

        return this.modelMapper.map(category, CategoryWrapper.class);
    }

    @Override
    public List<CategoryWrapper> getAllCategories() {
        List<Category> allCategories = this.categoryDao.findAll();
        List<CategoryWrapper> categoryWrappers = new ArrayList<>();
        for(Category category: allCategories) {
            CategoryWrapper categoryWrapper = this.modelMapper.map(category, CategoryWrapper.class);
            categoryWrappers.add(categoryWrapper);
        }
        return categoryWrappers;
    }
}
