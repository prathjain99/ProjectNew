package com.quantcrux.product.controller;

import com.quantcrux.product.dto.ProductRequest;
import com.quantcrux.product.entity.Product;
import com.quantcrux.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllActiveProducts();
        return ResponseEntity.ok(products);
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody ProductRequest request,
            @RequestHeader("X-User-Id") String userId) {
        try {
            Product product = productService.createProduct(request, userId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            System.err.println("Product creation failed: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    @GetMapping("/my-products")
    public ResponseEntity<List<Product>> getMyProducts(@RequestHeader("X-User-Id") String userId) {
        List<Product> products = productService.getProductsByCreator(userId);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product service is running");
    }
}