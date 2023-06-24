package com.zhongshijie1995.big_boot.stock.api;

import com.zhongshijie1995.big_boot.base.util.api.ApiGet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class StockAPI {

    @Value("${stock.market.sh}")
    private List<String> STOCK_MARKET_SH;

    @Value("${stock.market.sz}")
    private List<String> STOCK_MARKET_SZ;

    private final String REQ_URL = "http://qt.gtimg.cn/q=%s";

    @Resource
    private ApiGet apiGet;

    private String fixCod(String cod) throws Exception {
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
        throw new Exception("未获取到市场代码");
    }

    private Map<String, String> realtimeAPITranslate(String body) {
        // 准备结果集合
        Map<String, String> result = new LinkedHashMap<>();
        // 分割和解析
        String[] parts = body.split("~");
        result.put("股票名称", parts[1]);
        result.put("股票代码", parts[2]);
        result.put("当前价格", parts[3]);
        result.put("昨收", parts[4]);
        result.put("开盘", parts[5]);
        result.put("成交量(手)", parts[6]);
        result.put("外盘", parts[7]);
        result.put("内盘", parts[8]);
        result.put("买1(价)", parts[9]);
        result.put("买1(量)", parts[10]);
        result.put("买2(价)", parts[11]);
        result.put("买2(量)", parts[12]);
        result.put("买3(价)", parts[13]);
        result.put("买3(量)", parts[14]);
        result.put("买4(价)", parts[15]);
        result.put("买4(量)", parts[16]);
        result.put("买5(价)", parts[17]);
        result.put("买5(量)", parts[18]);
        result.put("卖1(价)", parts[19]);
        result.put("卖1(量)", parts[20]);
        result.put("卖2(价)", parts[21]);
        result.put("卖2(量)", parts[22]);
        result.put("卖3(价)", parts[23]);
        result.put("卖3(量)", parts[24]);
        result.put("卖4(价)", parts[25]);
        result.put("卖4(量)", parts[26]);
        result.put("卖5(价)", parts[27]);
        result.put("卖5(量)", parts[28]);
        result.put("最新逐笔成交", parts[29]);
        result.put("请求时间", parts[30]);
        result.put("涨跌", parts[31]);
        result.put("涨跌幅", parts[32]);
        result.put("最高", parts[33]);
        result.put("最低", parts[34]);
        result.put("最新价/成交量(手)/成交额(元)", parts[35]);
        result.put("换手率", parts[38]);
        result.put("ttm市盈率", parts[39]);
        result.put("振幅", parts[43]);
        result.put("流通市值(亿)", parts[44]);
        result.put("总市值(亿)", parts[45]);
        result.put("市净率", parts[46]);
        result.put("涨停价", parts[47]);
        result.put("跌停价", parts[48]);
        result.put("量比", parts[49]);
        result.put("均价", parts[51]);
        result.put("动态市盈率", parts[52]);
        result.put("静态市盈率", parts[53]);
        result.put("成交额(万)", parts[57]);
        // 返回结果集合
        return result;
    }

    public Map<String, String> realtime(String cod) throws Exception {
        // API请求
        String stock = fixCod(cod);
        String url = String.format(REQ_URL, stock);
        String body = apiGet.httpGetBody(url);
        // 翻译API结果
        return realtimeAPITranslate(body);
    }

    public List<Map<String, String>> realtime(List<String> cods) throws Exception {
        // 逐个补全代码
        List<String> fixCods = new ArrayList<>();
        for (String cod : cods) {
            fixCods.add(fixCod(cod));
        }
        // API请求
        String url = String.format(REQ_URL, String.join(",", fixCods));
        String body = apiGet.httpGetBody(url);
        // 逐个翻译API结果
        List<Map<String, String>> result = new ArrayList<>();
        String[] stocks = body.split(";");
        for (String stock : stocks) {
            result.add(realtimeAPITranslate(stock));
        }
        return result;
    }
}
