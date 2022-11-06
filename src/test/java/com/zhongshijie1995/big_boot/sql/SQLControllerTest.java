package com.zhongshijie1995.big_boot.sql;


import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

public class SQLControllerTest {

    @Mocked
    SQLController sqlController;

    @Test
    public void get() {
        new Expectations() {{
            sqlController.get("");
            result = "WhatTheHelp";
        }};
        System.out.println(sqlController.get(""));
    }
}
