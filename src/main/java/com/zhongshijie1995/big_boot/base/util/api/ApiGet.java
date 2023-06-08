package com.zhongshijie1995.big_boot.base.util.api;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiGet {
    public String httpGet(String url) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
        if (body == null) {
            throw new Exception("请求API异常");
        }
        return body.trim();
    }
}
