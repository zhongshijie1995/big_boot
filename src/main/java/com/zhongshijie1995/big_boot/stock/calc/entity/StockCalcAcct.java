package com.zhongshijie1995.big_boot.stock.calc.entity;

import com.zhongshijie1995.big_boot.base.util.sql.EntityWithUuid;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class StockCalcAcct extends EntityWithUuid {
    private String acctName;
    private BigDecimal commissionRate;
    private BigDecimal taxRate;
    private BigDecimal transferRate;
    private BigDecimal minCommission;
}
