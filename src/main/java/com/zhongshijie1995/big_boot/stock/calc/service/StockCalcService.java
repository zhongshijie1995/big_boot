package com.zhongshijie1995.big_boot.stock.calc.service;

import com.zhongshijie1995.big_boot.stock.bean.StockConstants;
import com.zhongshijie1995.big_boot.stock.bean.StockMarket;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcAcct;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcFund;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcResult;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Slf4j
@Service
public class StockCalcService {

    @Resource
    private StockCalcResult stockCalcResult;

    @Resource
    private StockMarket stockMarket;

    @Resource
    private StockConstants stockConstants;

    private BigDecimal multiplyToAmt(BigDecimal... nums) {
        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal num : nums) {
            result = result.multiply(num);
        }
        result = result.setScale(stockConstants.RMB_SCALE, RoundingMode.HALF_UP);
        return result;
    }

    private BigDecimal addToAmt(BigDecimal... nums) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal num : nums) {
            result = result.add(num);
        }
        result = result.setScale(stockConstants.RMB_SCALE, RoundingMode.HALF_UP);
        return result;
    }

    public StockCalcResult calc(StockCalcAcct acct, StockCalcFund fund, StockCalcTrade trade) throws Exception {
        // -------------------- 做多 --------------------
        // 计算做多金额
        BigDecimal buyAmt = multiplyToAmt(trade.getBuyPrice(), trade.getStockQuantity());
        // 计算做多费用
        BigDecimal buyFee = addToAmt(
                multiplyToAmt(buyAmt, acct.getCommissionRate())  // 交易佣金
        );
        buyFee = buyFee.max(acct.getMinCommission());
        // 计算做多总额
        BigDecimal buySum = addToAmt(buyAmt, buyFee);
        // 做多取贷记
        buySum = buySum.negate();
        // 打印做多阶段数据
        log.info("阶段[做多]-委托金额[{}]-产生费用[{}]-发生金额[{}]", buyAmt, buyFee, buySum);

        // -------------------- 做空 --------------------
        // 计算做空金额
        BigDecimal sellAmt = multiplyToAmt(trade.getSellPrice(), trade.getStockQuantity());
        // 计算做空费用
        BigDecimal sellFee = addToAmt(
                multiplyToAmt(sellAmt, acct.getCommissionRate()),  // 交易佣金
                stockMarket.isStockEtf(trade.getStockCode()) ? BigDecimal.ZERO : multiplyToAmt(sellAmt, acct.getTaxRate()) // 印花税
        );
        sellFee = sellFee.max(acct.getMinCommission());
        // 计算做空总额
        BigDecimal sellSum = addToAmt(sellAmt, sellFee.negate());
        // 做空作为借记
        // ---
        // 打印做空阶段数据
        log.info("阶段[做空]-委托金额[{}]-产生费用[{}]-发生金额[{}]", sellAmt, sellFee, sellSum);

        // -------------------- 资金 --------------------
        BigDecimal fundAmt;
        // 填补日期
        Date today = new Date();
        if (trade.getBuyDate() == null) {
            trade.setBuyDate(today);
        }
        if (trade.getSellDate() == null) {
            trade.setSellDate(today);
        }
        // 获取资金金额
        if (trade.getSellDate().before(trade.getBuyDate())) {
            fundAmt = sellSum.abs();
        } else {
            fundAmt = buySum.abs();
        }
        // 获取日期跨度
        BigDecimal daySum = BigDecimal.valueOf((trade.getBuyDate().getTime() - trade.getSellDate().getTime()) / (1000 * 60 * 60 * 24)).abs().add(trade.getUseDelay());
        // 计算资金成本
        BigDecimal fundFee = multiplyToAmt(fundAmt, fund.getPayRate(), daySum, stockConstants.DAY_DIVIDE_YEAR);
        // 资金作为贷记
        fundFee = fundFee.negate();
        // 打印资金阶段数据
        log.info("阶段[资金]-资金金额[{}]-计息天数[{}]-资金成本[{}]", fundAmt, daySum, fundFee);

        // -------------------- 汇总 --------------------
        BigDecimal netValue = addToAmt(buySum, sellSum, fundFee);

        // -------------------- 组织 --------------------
        stockCalcResult.setStockCalcAcct(acct);
        stockCalcResult.setStockCalcFund(fund);
        stockCalcResult.setStockCalcTrade(trade);
        stockCalcResult.setNetValue(netValue);
        return stockCalcResult;
    }
}
