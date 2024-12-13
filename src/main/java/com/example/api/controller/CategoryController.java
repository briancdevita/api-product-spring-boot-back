package com.example.api.controller;
import com.example.api.model.Category;
import com.example.api.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CategoryController {



    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    // Create category
    @PostMapping("/category/")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {

        if (category.getName() == null) {
            return ResponseEntity.notFound().build();
        }else {

            Category addCategory =  categoryService.createCategory(category);
            return ResponseEntity.ok().body(addCategory);
        }

    }

    // Get all category
    @GetMapping("/category/")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> category = categoryService.getAllCategory();
        return ResponseEntity.ok().body(category);
    }


    // Get one category
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getOneCategory(@PathVariable  Long id) {
        Category category = categoryService.getOneCategory(id);
        return ResponseEntity.ok().body(category);
    }



}
