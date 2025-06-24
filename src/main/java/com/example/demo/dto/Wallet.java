package com.example.demo.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Wallet {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private Double usdtNo;
    private Double btcNo;
    private Double ethNo; //etherum and bitcoin
    }
    

