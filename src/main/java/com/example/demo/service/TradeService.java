package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Price;
import com.example.demo.dto.Trade;
import com.example.demo.dto.Wallet;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.TradeRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class TradeService {

    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private PriceRepository priceRepo;

    @Autowired
    private TradeRepository tradeRepo;

    public String trade(Long userId, String pairs, String type, Double quantity) {
        Wallet wallet = walletRepo.findById(userId).orElseThrow();

        Price price = priceRepo.findTopByPairsOrderByTimeDesc(pairs);
        if (price == null) {
            return "Price data not available";
        }

        double amt = "BUY".equalsIgnoreCase(type) ? price.getAskPrice() : price.getBidPrice();
        double cost = quantity * amt;

        if ("BUY".equalsIgnoreCase(type)) {
            if (wallet.getUsdtNo() < cost) {
                return "Insufficient USDT";
            }
            wallet.setUsdtNo(wallet.getUsdtNo() - cost);
            if ("BTCUSDT".equalsIgnoreCase(pairs)) {
                wallet.setBtcNo(wallet.getBtcNo() + quantity);
            } else {
                wallet.setEthNo(wallet.getEthNo() + quantity);
            }
        } else {
            if ("BTCUSDT".equalsIgnoreCase(pairs)) {
                if (wallet.getBtcNo() < quantity) {
                    return "Insufficient BTC";
                }
                wallet.setBtcNo(wallet.getBtcNo() - quantity);
                wallet.setUsdtNo(wallet.getUsdtNo() + cost);
            } else {
                if (wallet.getEthNo() < quantity) {
                    return "Insufficient ETH";
                }
                wallet.setEthNo(wallet.getEthNo() - quantity);
                wallet.setUsdtNo(wallet.getUsdtNo() + cost);
            }
        }

        walletRepo.save(wallet);

        Trade trade = new Trade();
        trade.setUserId(userId);
        trade.setPairs(pairs);
        trade.setType(type);
        trade.setPrice(amt);
        trade.setQuantity(quantity);
        trade.setTime(LocalDateTime.now());

        tradeRepo.save(trade);

        return "Trade successful";
    }
}
