package com.zhongshijie1995.big_boot.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("hello")
    public String hello() {
        return "Hello";
    }

    @RequestMapping("version")
    public String version() {
        StringBuilder result = new StringBuilder();
        String data;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/version.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((data = bufferedReader.readLine()) != null) {
                result.append(data);
            }
        } catch (Exception e) {
            result.append(e.getMessage());
        }
        return result.toString();
    }

}
