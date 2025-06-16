package com.quantcrux.product.service;

import com.quantcrux.product.dto.ProductRequest;
import com.quantcrux.product.entity.Product;
import com.quantcrux.product.entity.ProductType;
import com.quantcrux.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }
    
    public List<Product> getProductsByCreator(String createdBy) {
        return productRepository.findByCreatedByAndIsActiveTrue(createdBy);
    }
    
    public Product createProduct(ProductRequest request, String userId) {
        Product product = new Product();
        product.setName(request.getName());
        product.setType(ProductType.valueOf(request.getType().toUpperCase()));
        product.setUnderlyingAsset(request.getUnderlyingAsset());
        product.setStrike(request.getStrike());
        product.setBarrier(request.getBarrier());
        product.setCoupon(request.getCoupon());
        product.setNotional(request.getNotional());
        product.setMaturityMonths(request.getMaturityMonths());
        product.setIssuer(request.getIssuer());
        product.setCurrency(request.getCurrency());
        product.setCreatedBy(userId);
        
        return productRepository.save(product);
    }
    
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}