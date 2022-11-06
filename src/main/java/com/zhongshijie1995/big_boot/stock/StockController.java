package com.zhongshijie1995.big_boot.stock;

import com.zhongshijie1995.big_boot.stock.entity.Trade;
import com.zhongshijie1995.big_boot.stock.util.TradeCalc;
import com.zhongshijie1995.big_boot.util.cost.CostReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("calcProfit")
    public BigDecimal calcProfit(@RequestBody Trade trade) {
//        // 准备数据
//        Trade trade = new Trade();
//        trade.setCode("601288");
//        trade.setName("农业银行");
//        trade.setScale(2);
//        trade.setInPrice(BigDecimal.valueOf(6.92));
//        trade.setOutPrice(BigDecimal.valueOf(7.40));
//        trade.setQuantity(BigDecimal.valueOf(3700));
//        trade.setCommissionRate(BigDecimal.valueOf(2.5 / 10000));
//        trade.setTaxRate(BigDecimal.valueOf(0.1 / 100));
//        trade.setTransferRate(BigDecimal.valueOf(0.001 / 100));
//        trade.setBorrowType("融资");
//        trade.setBorrowRate(BigDecimal.valueOf(6.99 / 100 / 365));
//        trade.setBorrowStartDate("20221103");
//        trade.setBorrowEndDate("20221103");
        // 计算交易结果
        BigDecimal amt = tradeCalc.calcTradeResult(trade);
        // 记录日志
        log.info("最终利润 {}", amt);
        // 返回账目
        return amt;
    }
}
