package com.zhongshijie1995.big_boot.bak.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
@Slf4j
public class StockRealTime {

    protected String sampleHttpGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
        if (body == null) {
            return "";
        }
        return body.trim();
    }

    protected String getMarket(String code) {
        String sz = "sz";
        String sh = "sh";
        if (code.startsWith("00") || code.startsWith("300")) {
            return sz;
        }
        return sh;
    }

    public BigDecimal price(String code) {
        BigDecimal result = BigDecimal.ZERO;
        String url = String.format("https://qt.gtimg.cn/q=s_%s%s", getMarket(code), code);
        log.info("请求实时股价[{}]", url);
        String requestBody = sampleHttpGet(url);
        log.info("获取到实时股价串[{}]", requestBody);
        if (requestBody.contains("~")) {
            result = new BigDecimal(requestBody.split("~")[3]);
        }
        log.info("获取到实时价格为[{}]", result);
        return result;
    }
}
