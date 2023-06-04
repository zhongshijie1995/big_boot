package com.zhongshijie1995.big_boot.bak;

import com.zhongshijie1995.big_boot.bak.entity.Trade;
import com.zhongshijie1995.big_boot.bak.util.TradeCalc;
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
        // 计算交易结果
        BigDecimal amt = tradeCalc.calcTradeResult(trade);
        // 记录日志
        log.info("最终利润 {}", amt);
        // 返回账目
        return amt;
    }
}
