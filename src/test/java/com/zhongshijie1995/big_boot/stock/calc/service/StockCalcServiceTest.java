package com.zhongshijie1995.big_boot.stock.calc.service;

import com.alibaba.fastjson2.JSONObject;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcAcct;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcFund;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcResult;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcTrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

@SpringBootTest
public class StockCalcServiceTest {

    @Autowired
    private StockCalcService stockCalcService;

    @Test
    public void calc() throws Exception {
        MockMvcBuilders.standaloneSetup(stockCalcService).build();
        String json;

        json = "{\n" +
                "  \"acctName\": \"\",\n" +
                "  \"commissionRate\": 0.00012,\n" +
                "  \"taxRate\": 0.001,\n" +
                "  \"transferRate\": 0.00001,\n" +
                "  \"minCommission\":5\n" +
                "}";
        StockCalcAcct stockCalcAcct = JSONObject.parseObject(json, StockCalcAcct.class);
        json = "{\n" +
                "    \"fundName\": \"借呗\",\n" +
                "    \"payRate\": 0.12775\n" +
                "  }";
        StockCalcFund stockCalcFund = JSONObject.parseObject(json, StockCalcFund.class);
        json = "{\n" +
                "    \"acctName\": \"平安-普通\",\n" +
                "    \"fundName\": \"借呗\",\n" +
                "    \"useDelay\": 0,\n" +
                "    \"stockCode\": \"159841\",\n" +
                "    \"stockName\": \"证券ETF\",\n" +
                "    \"stockQuantity\": 122900,\n" +
                "    \"buyDate\": \"2023-06-21\",\n" +
                "    \"buyPrice\": 0.813,\n" +
                "    \"sellDate\": \"2023-06-21\",\n" +
                "    \"sellPrice\": 0.814\n" +
                "  }";
        StockCalcTrade stockCalcTrade = JSONObject.parseObject(json, StockCalcTrade.class);

        StockCalcResult stockCalcResult = stockCalcService.calc(stockCalcAcct, stockCalcFund, stockCalcTrade);
        if (stockCalcResult.getNetValue().equals(BigDecimal.valueOf(98.91))) {
            throw new Exception("计算汇总金额不正确-001");
        }
    }
}
