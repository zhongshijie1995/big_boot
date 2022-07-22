package com.zhongshijie1995.big_boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.zhongshijie1995.big_boot.sql.mapper")
public class BigBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BigBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BigBootApplication.class);
    }
}
