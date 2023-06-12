package com.zhongshijie1995.big_boot.stock.schedule;

import com.zhongshijie1995.big_boot.stock.service.StockAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class starStockFetch {

    @Value("${stock.star}")
    private List<String> STOCK_STAR;

    @Resource
    private StockAPI stockAPI;

    @Scheduled(cron = "*/5 * * * * *")
    public void log() throws Exception {
        List<Map<String, String>> stocks = stockAPI.realtime(STOCK_STAR);
        for (Map<String, String> stock : stocks) {
            log.info("{}", stock);
        }
    }

}
