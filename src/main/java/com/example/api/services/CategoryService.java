package com.example.api.services;


import com.example.api.model.Category;
import com.example.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    //Create a category
    public Category createCategory(Category category) {
        Category addCategory = categoryRepository.save(category);
        return addCategory;
    }



    //Get all category
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }



    //Get one Category
    public Category getOneCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }







}
