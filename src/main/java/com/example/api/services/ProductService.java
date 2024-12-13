package com.example.api.services;
import com.example.api.DTO.Product;
import com.example.api.error.ApiError;
import com.example.api.exception.ApiException;
import com.example.api.model.Category;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private final CategoryRepository categoryRepository;
    ProductRepository productRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    //Create Product
    public com.example.api.model.Product createProduct(com.example.api.model.Product product) {

        if (productRepository.existsByName(product.getName())) {
            throw new ApiException(ApiError.RESOURCE_ALREADY_EXISTS);
        }


        if (product.getPrice() <= 0) {
            throw new ApiException(ApiError.BUSINESS_RULE_VIOLATION);
        }


        return productRepository.save(product);
    }



    // Get all products
    public List<com.example.api.model.Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (DataAccessException ex) {
            throw  new ApiException(ApiError.RESOURCE_NOT_FOUND);

        }
    }



    // Get one product
    public Product getOneProdcut(Long id) {
       com.example.api.model.Product product = productRepository.findById(id).orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND));

       if (product == null) {
           return null;
       } else {
           return new Product(
                   product.getName(),
                   product.getPrice(),
                   product.getDescription(),
                   product.getCategory().getId()
           );
       }
    }



    //Update product
    public com.example.api.model.Product updateProduct(Long id, Product newProductDTO) {
        com.example.api.model.Product currentProduct = productRepository.findById(id).orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND));

        if (currentProduct != null) {
            currentProduct.setName(newProductDTO.getName());
            currentProduct.setPrice(newProductDTO.getPrice());
            currentProduct.setDescription(newProductDTO.getDescription());
            if (newProductDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(newProductDTO.getCategoryId()).orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND));
                currentProduct.setCategory(category);
            }

            return productRepository.save(currentProduct);
        }

        return null;
    }



    //Delete product
    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND);
        }


        productRepository.deleteById(id);
    }


}
