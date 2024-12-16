package com.example.api.controller;


import com.example.api.DTO.Product;
import com.example.api.model.Category;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;
import com.example.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST})

public class ProductController {

    private final ProductRepository productRepository;
    ProductService productService;
    CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }






    //Create product
    @PostMapping("/products")
    public ResponseEntity<com.example.api.model.Product> createProduct(@RequestBody Product productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
        com.example.api.model.Product product = new com.example.api.model.Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        System.out.println(product);
        com.example.api.model.Product productAdd = productService.createProduct(product);
        return ResponseEntity.ok().build();
    }



    // Get all products
    @GetMapping("/products")
    public List<com.example.api.model.Product> getAllProducts() {
        List<com.example.api.model.Product> products = productService.getAllProducts();
        return products;
    }

    //Get one product
    @GetMapping("/product/{id}")
    public Product getOneProduct(@PathVariable Long id) {
        return productService.getOneProdcut(id);
    }


    // Update product
    @PutMapping("/product/{id}")
    public ResponseEntity<com.example.api.model.Product> updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        com.example.api.model.Product product = productService.updateProduct(id, newProduct);
        return ResponseEntity.ok().body(product);

    }


    //Delete product
    @DeleteMapping("/product/{id}")
    public ResponseEntity<com.example.api.model.Product> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }



}
