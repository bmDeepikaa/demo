package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.Trade;
import com.example.demo.dto.Wallet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api";
        logger.info("Base URL set to: {}", baseUrl);
    }

    @Test
    void contextLoads() {
        logger.info("Starting contextLoads test...");
        // Basic context startup test
        logger.info("contextLoads test completed successfully");
    }

    @Test
    void testInitialWalletBalance() {
        logger.info("Fetching wallet for user 1");
        Wallet wallet = restTemplate.getForObject(baseUrl + "/wallet/1", Wallet.class);
        logger.info("Wallet received: USDT={}, BTC={}, ETH={}", wallet.getUsdtNo(), wallet.getBtcNo(), wallet.getEthNo());

        assertThat(wallet).isNotNull();
        assertThat(wallet.getUsdtNo()).isEqualTo(50000.0);
        assertThat(wallet.getBtcNo()).isEqualTo(0.0);
        assertThat(wallet.getEthNo()).isEqualTo(0.0);
    }

    @Test
    void testTradeBuyBTC() {
        logger.info("Attempting to BUY 0.001 BTC for user 1");
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/trade?userId=1&pairs=BTCUSDT&type=BUY&quantity=0.001",
                null,
                String.class
        );
        logger.info("Trade response status: {}, body: {}", response.getStatusCode().value(), response.getBody());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("Trade successful");

        Wallet wallet = restTemplate.getForObject(baseUrl + "/wallet/1", Wallet.class);
        logger.info("Updated wallet after trade: USDT={}, BTC={}", wallet.getUsdtNo(), wallet.getBtcNo());

        assertThat(wallet).isNotNull();
        assertThat(wallet.getBtcNo()).isGreaterThan(0.0);
        assertThat(wallet.getUsdtNo()).isLessThan(50000.0);
    }

    @Test
    void testTradeFailInsufficientUSDT() {
        logger.info("Attempting to BUY 1000 BTC (expected to fail due to insufficient USDT) for user 1");
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl + "/trade?userId=1&pairs=BTCUSDT&type=BUY&quantity=1000",
                null,
                String.class
        );
        logger.info("Trade fail response status: {}, body: {}", response.getStatusCode().value(), response.getBody());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("Insufficient USDT");
    }

    @Test
    void testTradeHistory() {
        logger.info("Fetching trade history for user 1");
        Trade[] trades = restTemplate.getForObject(baseUrl + "/trades/1", Trade[].class);
        logger.info("Number of trades found: {}", trades != null ? trades.length : "null");

        assertThat(trades).isNotNull();
    }
}
