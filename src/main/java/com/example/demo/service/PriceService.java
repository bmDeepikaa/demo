package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Price;
import com.example.demo.repository.PriceRepository;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    public Price getBestPrice(String pairs) {
        return priceRepo.findTopByPairsOrderByTimeDesc(pairs);
    }

    public void savePrice(Price price) {
        priceRepo.save(price); // Ensure PriceRepository extends JpaRepository<Price, Long>
    }

    @Scheduled(fixedRate = 10000)
    public void fetchPriceAndStore() throws IOException {
        try {
            String binanceUrl = "https://api.binance.com/api/v3/ticker/bookTicker";
            BinancePrice[] prices = restTemplate.getForObject(binanceUrl, BinancePrice[].class);

            if (prices != null) {
                for (BinancePrice price : prices) {
                    if ("BTCUSDT".equals(price.getPairs()) || "ETHUSDT".equals(price.getPairs())) {
                        Price p = new Price();
                        p.setPairs(price.getPairs());
                        p.setBidPrice(Double.parseDouble(price.getBidPrice()));
                        p.setAskPrice(Double.parseDouble(price.getAskPrice()));
                        p.setTime(LocalDateTime.now());
                        priceRepo.save(p);
                    }
                }
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    static class BinancePrice {
        private String pairs;
        private String bidPrice;
        private String askPrice;

        public String getPairs() {
            return pairs;
        }

        public String getBidPrice() {
            return bidPrice;
        }

        public String getAskPrice() {
            return askPrice;
        }
    }
}
