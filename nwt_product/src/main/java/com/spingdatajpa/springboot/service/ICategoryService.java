package com.spingdatajpa.springboot.service;

import com.spingdatajpa.springboot.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    public Category createCategory(Category category);
    public Category updateCategory(int id, Category category);
    public void deleteCategory(int id);
    public Category getCategory(Integer id);
    public List<Category> getAllCategories();
}
