package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Trade;

@Repository
public interface  TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByUserId(String userId);
    List<Trade> findByPair(String pair);
    List<Trade> findByUserIdOrderByTimestampDesc(Long userId);

    
    
}
