package com.quantcrux.portfolio.repository;

import com.quantcrux.portfolio.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, String> {
    List<Position> findByUserIdAndIsActiveTrue(String userId);
    List<Position> findByProductId(String productId);
    List<Position> findByUserId(String userId);
}