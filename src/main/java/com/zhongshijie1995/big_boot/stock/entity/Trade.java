package com.zhongshijie1995.big_boot.stock.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Trade {
    // 货币精度
    private int scale;
    // 交易信息
    private String code;
    private String name;
    // 出入价
    private BigDecimal inPrice;
    private BigDecimal outPrice;
    // 数量
    private BigDecimal quantity;
    // 费率
    private BigDecimal commissionRate;
    private BigDecimal taxRate;
    private BigDecimal transferRate;
    // 融资融券
    private String borrowType;
    private BigDecimal borrowRate;
    private String borrowStartDate;
    private String borrowEndDate;
}
