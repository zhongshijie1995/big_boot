package com.zhongshijie1995.big_boot.stock.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockMarket {

    @Value("${stock.market.sh}")
    private List<String> STOCK_MARKET_SH;

    @Value("${stock.market.sz}")
    private List<String> STOCK_MARKET_SZ;

    @Value("${stock.market.etfSh}")
    private List<String> STOCK_MARKET_ETF_SH;

    @Value("${stock.market.etfSz}")
    private List<String> STOCK_MARKET_ETF_SZ;

    public String fixCod(String cod) throws Exception {
        if (cod.length() != 6) {
            throw new Exception("股票代码位数不正确");
        }
        for (String codHead : STOCK_MARKET_SH) {
            if (cod.startsWith(codHead)) {
                return "sh" + cod;
            }
        }
        for (String codHead : STOCK_MARKET_SZ) {
            if (cod.startsWith(codHead)) {
                return "sz" + cod;
            }
        }
        for (String codHead : STOCK_MARKET_ETF_SH) {
            if (cod.startsWith(codHead)) {
                return "sz" + cod;
            }
        }
        for (String codHead : STOCK_MARKET_ETF_SZ) {
            if (cod.startsWith(codHead)) {
                return "sz" + cod;
            }
        }
        throw new Exception("未获取到市场代码");
    }

    public boolean isStockEtf(String cod) throws Exception {
        if (cod.length() != 6) {
            throw new Exception("股票代码位数不正确");
        }
        for (String codHead : STOCK_MARKET_ETF_SH) {
            if (cod.startsWith(codHead)) {
                return true;
            }
        }
        for (String codHead : STOCK_MARKET_ETF_SZ) {
            if (cod.startsWith(codHead)) {
                return true;
            }
        }
        return false;
    }
}
