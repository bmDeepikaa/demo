package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Price;
import com.example.demo.dto.Trade;
import com.example.demo.dto.Wallet;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.TradeRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.TradeService;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired private PriceRepository priceRepo;
    @Autowired private WalletRepository walletRepo;
    @Autowired private TradeRepository tradeRepo;
    @Autowired private TradeService tradeService;

    @GetMapping("/prices/{pairs}")
    public Price getPrice(@PathVariable String pairs) {
        return priceRepo.findTopByPairsOrderByTimeDesc(pairs);
    }

    @PostMapping("/trade")
    public String trade(@RequestParam Long userId,
                        @RequestParam String pairs,
                        @RequestParam String type,
                        @RequestParam Double quantity) {
        return tradeService.trade(userId, pairs, type, quantity);
    }

    @GetMapping("/wallet/{userId}")
    public Wallet getWallet(@PathVariable Long userId) {
        return walletRepo.findById(userId).orElse(null);
    }

    @GetMapping("/trades/{userId}")
    public List<Trade> getTrades(@PathVariable Long userId) {
        return tradeRepo.findByUserIdOrderByTimestampDesc(userId);
    }
}
