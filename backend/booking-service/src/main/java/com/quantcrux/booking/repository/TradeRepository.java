package com.quantcrux.booking.repository;

import com.quantcrux.booking.entity.Trade;
import com.quantcrux.booking.entity.TradeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {
    List<Trade> findByUserIdOrderByTradeeDateDesc(String userId);
    List<Trade> findByProductId(String productId);
    List<Trade> findByStatus(TradeStatus status);
    List<Trade> findByUserIdAndStatus(String userId, TradeStatus status);
}