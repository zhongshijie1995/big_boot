package com.zhongshijie1995.big_boot.stock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class StockTaskBase {

    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    private List<Map<String, String>> stocks;

    public void setStocks(List<Map<String, String>> stocks) {
        this.stocks = stocks;
    }

    public void start() {
        if (stocks.size() == 0) {
            log.info("无任务需执行");
            return;
        }
        for (Map<String, String> stock : stocks) {
            executorService.submit(() -> task(stock));
        }
    }

    public void task(Map<String, String> stock) {
        log.info("{}", stock);
    }
}
