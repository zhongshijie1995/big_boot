package com.zhongshijie1995.big_boot.stock.bean;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockConstants {
    public final int RMB_SCALE = 2;

    public final BigDecimal DAY_DIVIDE_YEAR = BigDecimal.valueOf(1.0 / 365.0);
}
