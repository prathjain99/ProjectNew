package com.quantcrux.product.repository;

import com.quantcrux.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByIsActiveTrue();
    List<Product> findByCreatedByAndIsActiveTrue(String createdBy);
    List<Product> findByUnderlyingAsset(String underlyingAsset);
}