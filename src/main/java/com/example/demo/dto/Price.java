package com.example.demo.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Price {
    @Id @GeneratedValue
    private Long id;
    private String pairs;  // match the parameter in controller and repo methods
    private Double bidPrice;
    private Double askPrice;
    private LocalDateTime time;
}
