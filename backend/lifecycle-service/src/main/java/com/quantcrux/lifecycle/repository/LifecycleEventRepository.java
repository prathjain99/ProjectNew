package com.quantcrux.lifecycle.repository;

import com.quantcrux.lifecycle.entity.EventStatus;
import com.quantcrux.lifecycle.entity.EventType;
import com.quantcrux.lifecycle.entity.LifecycleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifecycleEventRepository extends JpaRepository<LifecycleEvent, String> {
    List<LifecycleEvent> findByTradeIdOrderByEventDateDesc(String tradeId);
    List<LifecycleEvent> findByProductId(String productId);
    List<LifecycleEvent> findByEventTypeAndStatus(EventType eventType, EventStatus status);
    List<LifecycleEvent> findByStatus(EventStatus status);
}