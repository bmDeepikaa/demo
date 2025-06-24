package com.example.demo;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.dto.Price;
import com.example.demo.dto.Wallet;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.WalletRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private WalletRepository walletRepo;
    @Autowired private PriceRepository priceRepo;

    @Override
    public void run(String... args) {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setUsername("testuser");
        wallet.setPassword("123");
        wallet.setUsdtNo(50000.0);
        wallet.setBtcNo(0.0);
        wallet.setEthNo(0.0);
        walletRepo.save(wallet);

        Price price = new Price();
        price.setPairs("BTCUSDT");
        price.setAskPrice(50000.0); // For BUY
        price.setBidPrice(49900.0); // For SELL
        price.setTime(LocalDateTime.now());
        priceRepo.save(price);
    }
}
