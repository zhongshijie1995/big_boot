package com.zhongshijie1995.big_boot.stock;

import com.zhongshijie1995.big_boot.stock.entity.Trade;
import com.zhongshijie1995.big_boot.stock.util.TradeCalc;
import com.zhongshijie1995.big_boot.util.cost.CostReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Api(tags = "股票")
@RestController
@RequestMapping("stock")
@Slf4j
public class StockController {

    @Resource
    private TradeCalc tradeCalc;

    @CostReport
    @ApiOperation("计算利润")
    @GetMapping("calcProfit")
    public BigDecimal calcProfit(@RequestBody Trade trade) {
//        // 准备数据
//        Trade tc = new Trade();
//        tc.setCode("601288");
//        tc.setName("农业银行");
//        tc.setScale(2);
//        tc.setInPrice(BigDecimal.valueOf(6.92));
//        tc.setOutPrice(BigDecimal.valueOf(7.40));
//        tc.setQuantity(BigDecimal.valueOf(3700));
//        tc.setCommissionRate(BigDecimal.valueOf(2.5/10000));
//        tc.setTaxRate(BigDecimal.valueOf(0.1/100));
//        tc.setTransferRate(BigDecimal.valueOf(0.001/100));
//        tc.setBorrowType("融资");
//        tc.setBorrowRate(BigDecimal.valueOf(6.99/100/365));
//        tc.setBorrowStartDate("20221103");
//        tc.setBorrowEndDate("20221103");
        // 计算交易结果
        BigDecimal amt = tradeCalc.calcTradeResult(trade);
        // 记录日志
        log.info("最终利润 {}", amt);
        // 返回账目
        return amt;
    }
}
