package com.zhongshijie1995.liquibase.term;

import java.util.Scanner;

/**
 * 作答类：输入题
 */
public class TermInput {
    private final String title;
    private final Scanner scanner;

    public TermInput(String title, Scanner scanner) {
        this.title = title;
        this.scanner = scanner;
    }

    public String ask() {
        System.out.println("------------------------------------");
        System.out.println(title);
        System.out.print("请输入 > ");
        return scanner.nextLine();
    }
}
