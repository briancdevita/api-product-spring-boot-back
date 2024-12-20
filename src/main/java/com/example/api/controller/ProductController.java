package com.example.api.controller;

import com.example.api.DTO.Product;
import com.example.api.model.Category;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;
import com.example.api.services.ProductService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;


@RestController()
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST})

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
    public ResponseEntity<com.example.api.model.Product> deleteProduct(@PathVariable Long id, @RequestHeader("Authorization")String authHeader) {
        System.out.println("Cabecera de autorización recibida: " + authHeader);

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/download")
    public ResponseEntity<byte[]> downloadCsv() {
        List<com.example.api.model.Product> products = productService.getAllProducts();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Writer writer = new OutputStreamWriter(byteArrayOutputStream);
             CSVWriter csvWriter = new CSVWriter(writer)) {

            // Escribir encabezados
            String[] header = {"ID", "Nombre", "Precio", "Descripción", "Categoría"};
            csvWriter.writeNext(header);

            // Escribir datos de productos
            for (com.example.api.model.Product product : products) {
                String categoryName = product.getCategory() != null ? product.getCategory().getName() : "Sin categoría";
                String[] data = {
                        String.valueOf(product.getId()),
                        product.getName(),
                        String.valueOf(product.getPrice()),
                        product.getDescription(),
                        categoryName
                };
                csvWriter.writeNext(data);
            }

            csvWriter.flush();

            byte[] csvData = byteArrayOutputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=productos.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el archivo CSV".getBytes());
        }
    }

}
