package com.spingdatajpa.springboot.controller;

import com.spingdatajpa.springboot.entity.Category;
import com.spingdatajpa.springboot.error.exception.WrappedException;
import com.spingdatajpa.springboot.service.impl.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spingdatajpa.springboot.error.ErrorConstants.NOT_FOUND;


@RestController
@RequestMapping("product/category/")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("get/{id}")
    @ResponseBody
    public Category getCategory(@PathVariable("id") int id, HttpServletRequest request){
        Category category = categoryService.getCategory(id);
        if(category==null) {
            throw new WrappedException(NOT_FOUND);
        }
        return category;
    }
    @DeleteMapping("delete/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteCategory(@PathVariable("id") int id){
        Category category = categoryService.getCategory(id);
        if(category==null){
            throw new WrappedException(NOT_FOUND);
        }
        categoryService.deleteCategory(id);
    }
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @PutMapping("update/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category){
        Category category1 = categoryService.getCategory(id);
        if(category1==null){
            throw new WrappedException(NOT_FOUND);
        }
        return categoryService.updateCategory(id, category);
    }
    @PostMapping("/create")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

}
