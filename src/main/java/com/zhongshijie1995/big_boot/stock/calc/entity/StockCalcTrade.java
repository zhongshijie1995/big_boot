package com.zhongshijie1995.big_boot.stock.calc.entity;

import com.zhongshijie1995.big_boot.base.util.sql.EntityWithUuid;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class StockCalcTrade extends EntityWithUuid {
    private String acctName;
    private String fundName;
    private BigDecimal useDelay;
    private String stockCode;
    private String stockName;
    private BigDecimal stockQuantity;
    private Date buyDate;
    private BigDecimal buyPrice;
    private Date sellDate;
    private BigDecimal sellPrice;
}
