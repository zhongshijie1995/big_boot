package com.zhongshijie1995.big_boot.stock.calc.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class StockCalcResult {
    private StockCalcAcct stockCalcAcct;
    private StockCalcFund stockCalcFund;
    private StockCalcTrade stockCalcTrade;
    private BigDecimal netValue;
}
