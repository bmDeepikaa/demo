package com.example.demo.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Trade {
    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private String pairs;
    private String type;  // "BUY" or "SELL"
    private Double price;
    private Double quantity;
    private LocalDateTime time;
}
