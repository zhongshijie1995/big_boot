package com.zhongshijie1995.big_boot.info;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "version")
@PropertySource(value = "classpath:info.yaml")
public class InfoProperties {
    @Value("${version}")
    private String version;

    @Value("${time}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
