package com.application.blog.dao;

import com.application.blog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
    boolean existsByCategoryTitle(String categoryTitle);
}
