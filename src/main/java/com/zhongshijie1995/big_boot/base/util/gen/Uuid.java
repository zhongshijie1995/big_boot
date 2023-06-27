package com.zhongshijie1995.big_boot.base.util.gen;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Uuid {
    public String gen() {
        return UUID.randomUUID().toString();
    }
}
