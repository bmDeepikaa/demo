package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Wallet;

@Repository
public interface  WalletRepository  extends JpaRepository<Wallet, Long> {

}
