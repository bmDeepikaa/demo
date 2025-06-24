package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository; //does the simple findAll() and findById() methods

import com.example.demo.dto.Price;

public interface  PriceRepository extends JpaRepository<Price, Long> {
    
    Price findTopByPairsOrderByTimeDesc(String pairs);
    Price save();

}
