package com.quantcrux.strategy.repository;

import com.quantcrux.strategy.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, String> {
    List<Strategy> findByCreatedBy(String createdBy);
}