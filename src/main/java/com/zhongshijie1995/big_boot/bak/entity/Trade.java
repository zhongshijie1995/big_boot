package com.zhongshijie1995.big_boot.bak.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "Trade", description = "交易的描述体")
public class Trade {
    @ApiModelProperty(value = "货币精度", name = "scale", example = "2")
    private int scale;
    @ApiModelProperty(value = "股票代码", name = "code", example = "601288")
    private String code;
    @ApiModelProperty(value = "股票名称", name = "name", example = "农业银行")
    private String name;
    @ApiModelProperty(value = "入价", name = "inPrice", example = "2.73")
    private BigDecimal inPrice;
    @ApiModelProperty(value = "出价", name = "outPrice", example = "2.75")
    private BigDecimal outPrice;
    @ApiModelProperty(value = "数量", name = "quantity", example = "35000")
    private BigDecimal quantity;
    @ApiModelProperty(value = "佣金", name = "commissionRate", example = "0.00025")
    private BigDecimal commissionRate;
    @ApiModelProperty(value = "印花税", name = "taxRate", example = "0.001")
    private BigDecimal taxRate;
    @ApiModelProperty(value = "过户费", name = "transferRate", example = "0.00001")
    private BigDecimal transferRate;
    @ApiModelProperty(value = "融资/普通", name = "borrowType", example = "融资")
    private String borrowType;
    @ApiModelProperty(value = "融资利率（日化）", name = "borrowRate", example = "0.00019151")
    private BigDecimal borrowRate;
    @ApiModelProperty(value = "融资起息日", name = "borrowStartDate", example = "20221101")
    private String borrowStartDate;
    @ApiModelProperty(value = "融资到期日", name = "borrowEndDate", example = "20221103")
    private String borrowEndDate;
}
