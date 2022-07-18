package com.zhongshijie1995.liquibase.term;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 作答类：选择题
 */
public class TermChoice {

    private final String title;
    private final Map<Integer, String> items;
    private final Map<Integer, String> answers;

    private final Scanner scanner;

    public TermChoice(String title, Scanner scanner) {
        this.title = title;
        this.items = new HashMap<>();
        this.answers = new HashMap<>();
        this.scanner = scanner;
    }

    public String ask() {
        int choose = -1;
        do {
            if (choose == -1) {
                System.out.println("------------------------------------");
                System.out.println(title);
                for (Map.Entry<Integer, String> item : items.entrySet()) {
                    String show = String.format("[%d] %s", item.getKey(), item.getValue());
                    System.out.println(show);
                }
                System.out.print("请选择 > ");
            } else {
                System.out.print("输入有误，请选择 > ");
            }
            choose = Integer.parseInt(scanner.nextLine());
        } while (!answers.containsKey(choose));
        return answers.get(choose);
    }

    public void addItem(String choice, String answer) {
        int id = items.size() + 1;
        items.put(id, choice);
        answers.put(id, answer);
    }
}
