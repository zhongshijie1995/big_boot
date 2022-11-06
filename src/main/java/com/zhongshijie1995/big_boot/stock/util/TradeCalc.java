package com.zhongshijie1995.big_boot.stock.util;

import com.zhongshijie1995.big_boot.stock.entity.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class TradeCalc {
    protected BigDecimal calcEntrustAmt(BigDecimal price, BigDecimal quantity, boolean isNegate) {
        // 初始化账目
        BigDecimal amt;
        // 计算委托金额
        amt = price.multiply(quantity);
        // 确定金额方向
        amt = isNegate ? amt.negate() : amt;
        // 返回账目
        return amt;
    }

    protected BigDecimal calcFeeByRate(BigDecimal amt, BigDecimal feeRates, BigDecimal leastFee, int scale, RoundingMode roundingMode) {
        // 初始化账目
        BigDecimal fee;
        // 计算费率
        fee = amt.abs().multiply(feeRates);
        // 保留小数
        fee = calcAmtScale(fee, scale, roundingMode);
        // 确认最低消费
        fee = fee.max(leastFee);
        // 返回账目
        return fee;
    }

    protected BigDecimal clacAmtSum(List<BigDecimal> amtList) {
        // 初始化账目
        BigDecimal result;
        result = BigDecimal.ZERO;
        // 累加金额
        for (BigDecimal amt : amtList) {
            result = result.add(amt);
        }
        // 返回账目
        return result;
    }

    protected BigDecimal calcTradeAmt(BigDecimal price, BigDecimal quantity, List<BigDecimal> feeRates, List<BigDecimal> leastFees, List<Integer> scales, List<RoundingMode> roundingModes, boolean isNegate) {
        // 初始化账目
        BigDecimal tradeAmt;
        // 计算委托金额
        BigDecimal entrustAmt = calcEntrustAmt(price, quantity, isNegate);
        // 计算费率
        List<BigDecimal> fees = new ArrayList<>();
        for (int i = 0; i < feeRates.size(); i++) {
            BigDecimal tmp;
            tmp = calcFeeByRate(entrustAmt, feeRates.get(i), leastFees.get(i), scales.get(i), roundingModes.get(i));
            fees.add(tmp);
        }
        BigDecimal feeAmt = clacAmtSum(fees);
        // 记录日志
        log.info("委托金融 [{}] 扣除费用 [{}={}]", entrustAmt, fees, feeAmt);
        // 委托金额扣除费用
        tradeAmt = entrustAmt.subtract(feeAmt);
        // 返回账目
        return tradeAmt;
    }

    protected BigDecimal calcAmtScale(BigDecimal amt, int scale, RoundingMode roundingMode) {
        // 初始化账目
        BigDecimal result;
        // 切换精度
        result = amt.setScale(scale, roundingMode);
        // 返回账目
        return result;
    }

    protected BigDecimal calcDatePass(String startDateStr, String endDateStr) {
        // 初始化天数差
        BigDecimal days = BigDecimal.ZERO;
        // 指定日期转换格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            // 解析日期字符串
            Date startDate = simpleDateFormat.parse(startDateStr);
            Date endDate = simpleDateFormat.parse(endDateStr);
            // 设定日期计算器
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            // 计算日期
            days = BigDecimal.valueOf(((int) (startCalendar.getTime().getTime() / 1000) - (int) (endCalendar.getTime().getTime() / 1000)) / (60 * 60 * 24)).abs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public BigDecimal calcTradeResult(Trade trade) {
        // 初始化账目
        BigDecimal result;
        // 初始化交易过程
        List<BigDecimal> amt = new ArrayList<>();
        // 初始化计费标准列表
        List<BigDecimal> feeRates = new ArrayList<>();
        List<BigDecimal> leastFees = new ArrayList<>();
        List<Integer> scales = new ArrayList<>();
        List<RoundingMode> roundingModes = new ArrayList<>();
        BigDecimal tmp;
        // 佣金（含规费）
        feeRates.add(trade.getCommissionRate());
        leastFees.add(BigDecimal.valueOf(5));
        scales.add(trade.getScale());
        roundingModes.add(RoundingMode.HALF_UP);
        // 过户费
        feeRates.add(trade.getTransferRate());
        leastFees.add(BigDecimal.ZERO);
        scales.add(trade.getScale());
        roundingModes.add(RoundingMode.HALF_UP);
        // ------------------------ 买入 ------------------------
        tmp = calcTradeAmt(trade.getInPrice(), trade.getQuantity(), feeRates, leastFees, scales, roundingModes, true);
        log.info("买入发生额 [{}]", tmp);
        amt.add(tmp);
        // ------------------------------------------------------
        // ------------------------ 融资 ------------------------
        // 融资费
        if (trade.getBorrowType().equals("融资")) {
            tmp = calcFeeByRate(tmp, trade.getBorrowRate(), BigDecimal.ZERO, trade.getScale(), RoundingMode.HALF_UP).negate();
            tmp = tmp.multiply(calcDatePass(trade.getBorrowStartDate(), trade.getBorrowEndDate()));
            tmp = calcAmtScale(tmp, trade.getScale(), RoundingMode.HALF_UP);
            log.info("融资发生额 [{}]", tmp);
            amt.add(tmp);
        }
        // ------------------------------------------------------
        // 印花税
        feeRates.add(trade.getTaxRate());
        leastFees.add(BigDecimal.ZERO);
        scales.add(trade.getScale());
        roundingModes.add(RoundingMode.HALF_UP);
        // ------------------------ 卖出 ------------------------
        tmp = calcTradeAmt(trade.getOutPrice(), trade.getQuantity(), feeRates, leastFees, scales, roundingModes, false);
        log.info("卖出发生额 [{}]", tmp);
        amt.add(tmp);
        // ------------------------------------------------------
        // 汇总账目
        log.info("汇总如下账目 [{}]", amt);
        result = clacAmtSum(amt);
        // 返回账目
        return result;
    }
}
