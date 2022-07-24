package com.zhongshijie1995.liquibase;

import com.zhongshijie1995.liquibase.term.TermChoice;
import com.zhongshijie1995.liquibase.term.TermController;
import com.zhongshijie1995.liquibase.term.TermInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LiquibaseCli {

    // Liquibase目录
    public static final String BASE_PATH = "db";
    public static final String LIQUIBASE = "liquibase/liquibase";

    // 键入控制器
    public static Scanner sc = new Scanner(System.in);

    /**
     * 入口函数
     *
     * @param args 任意入口参数
     */
    public static void main(String[] args) {
        TermController termController = new TermController(); // 初始化终端交互器
        String cmd = termController.appendCmd(LIQUIBASE, getParams());// 获取参数
        termController.runCmd(BASE_PATH, cmd); // 运行命令
    }

    /**
     * 获取参数
     *
     * @return 参数列表
     */
    public static List<String> getParams() {
        // 准备参数列表
        List<String> params = new ArrayList<>();

        TermChoice questionA = new TermChoice("A.选择操作类型", sc);
        questionA.addItem("更新与回滚", "change");
        questionA.addItem("打标记", "tag");
        questionA.addItem("比较", "diff");
        questionA.addItem("生成变更文档", "db-doc");
        String answerA = questionA.ask();
        switch (answerA) {
            case "change":
                // 问题AA
                TermChoice questionAA = new TermChoice("选择支持的类型", sc);
                questionAA.addItem("更新至（现在）", "update");
                questionAA.addItem("回滚至（日期 YYYY-MM-DD HH:MM:SS）", "rollback-to-date");
                questionAA.addItem("更新至（变更数）", "update-count");
                questionAA.addItem("回滚至（变更数）", "rollback-count");
                questionAA.addItem("更新至（基线名）", "update-to-tag");
                questionAA.addItem("回滚至（基线名）", "rollback");
                String answerAA = questionAA.ask();
                params.add(answerAA);
                if (!"update".equals(answerAA)) {
                    TermInput questionAAA = new TermInput("提供操作值", sc);
                    String answerAAA = questionAAA.ask();
                    params.add(answerAAA);
                }
                break;
            case "tag":
                params.add(answerA);
                TermInput subInput = new TermInput("提供基线名", sc);
                String subInputAnswer = subInput.ask();
                params.add(subInputAnswer);
                break;
            default:
                params.add(answerA);
        }
        return params;
    }

}
