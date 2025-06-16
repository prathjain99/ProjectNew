package com.quantcrux.backtest.repository;

import com.quantcrux.backtest.entity.BacktestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BacktestResultRepository extends JpaRepository<BacktestResult, String> {
    List<BacktestResult> findByUserIdOrderByCreatedAtDesc(String userId);
    List<BacktestResult> findByStrategyId(String strategyId);
}