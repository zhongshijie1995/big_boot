package com.zhongshijie1995.big_boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LiquibaseCli {

    // 操作系统环境
    public static final String WIN = "win";
    public static final String UNIX = "unix";
    public static final String WIN_CLI = "powershell.exe";
    public static final String UNIX_CLI = "/bin/sh";

    // Liquibase目录
    public static final String BASE_PATH = "db";
    public static final String LIQUIBASE = "./liquibase/liquibase";
    public static final String SPACE = " ";

    // 辅助内容
    public static final String I = "CLI";
    public static final String GBK = "GBK";

    // 日志控制器
    private static final Logger logger = LoggerFactory.getLogger(LiquibaseCli.class);

    // 键入控制器
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // 获取参数，运行命令
        runCmd(BASE_PATH, getCmd(getParams()));
    }

    public static List<String> getParams() {
        List<String> params = new ArrayList<>();
        ChoiceQuestion mainType = new ChoiceQuestion("选择操作类型");
        mainType.giveItem("更新与回滚", "change");
        mainType.giveItem("基线标记", "tag");
        mainType.giveItem("差异比较", "diff");
        mainType.giveItem("生成变更文档", "db-doc");
        String mainTypeAnswer = mainType.ask();

        if (mainTypeAnswer.equals("change")) {
            ChoiceQuestion subType = new ChoiceQuestion(String.format("选择支持的类型", mainTypeAnswer));
            subType.giveItem("更新至（现在）", "update");
            subType.giveItem("回滚至（日期 YYYY-MM-DD HH:MM:SS）", "rollback-to-date");
            subType.giveItem("更新至（变更数）", "update-count");
            subType.giveItem("回滚至（变更数）", "rollback-count");
            subType.giveItem("更新至（基线名）", "update-to-tag");
            subType.giveItem("回滚至（基线名）", "rollback");
            String subTypeAnswer = subType.ask();
            params.add(subTypeAnswer);
            if (!subTypeAnswer.equals("update")) {
                InputQuestion subInput = new InputQuestion("提供操作值");
                String subInputAnswer = subInput.ask();
                params.add(subInputAnswer);
            }
        }

        if (mainTypeAnswer.equals("tag")) {
            params.add(mainTypeAnswer);
            InputQuestion subInput = new InputQuestion("提供基线名");
            String subInputAnswer = subInput.ask();
            params.add(subInputAnswer);
        }

        if (mainTypeAnswer.equals("diff") || mainTypeAnswer.equals("db-doc")) {
            params.add(mainTypeAnswer);
        }

        return params;
    }

    static class ChoiceQuestion {
        private String title;
        private Map<Integer, String> items;
        private Map<Integer, String> answers;

        public ChoiceQuestion(String title) {
            this.title = title;
            this.items = new HashMap<>();
            this.answers = new HashMap<>();
        }

        public String ask() {
            logger.debug("[{}] 进入操作 {}", I, title);
            Integer choose = -1;
            do {
                if (choose == -1) {
                    System.out.println("------------------------------------");
                    System.out.println(title);
                    for (Map.Entry<Integer, String> item: items.entrySet()) {
                        String show = String.format("[%d] %s", item.getKey(), item.getValue());
                        System.out.println(show);
                    }
                    System.out.print("请选择 > ");
                } else {
                    System.out.print("输入有误，请选择 > ");
                }
                choose = Integer.valueOf(sc.nextLine());
            } while (!answers.containsKey(choose));
            System.out.println("------------------------------------");
            logger.debug("[{}] 已选择 {} {}", I,choose, answers.get(choose));
            return answers.get(choose);
        }

        public void giveItem(String choice, String answer) {
            int id = items.size() + 1;
            items.put(id, choice);
            answers.put(id, answer);
        }
    }

    static class InputQuestion {
        private String title;

        public InputQuestion(String title) {
            this.title = title;
        }

        public String ask() {
            logger.debug("[{}] 进入操作 {}", I, title);
            System.out.println("------------------------------------");
            System.out.println(title);
            System.out.print("请输入 > ");
            String input = sc.nextLine();
            System.out.println("------------------------------------");
            logger.debug("[{}] 已输入 {}", I, input);
            return input;
        }
    }

    public static String getCmd(List<String> params) {
        StringBuilder cmd = new StringBuilder();
        cmd.append(getOSPath(LIQUIBASE));
        for (String param: params) {
            cmd.append(SPACE);
            cmd.append(param);
        }
        return cmd.toString();
    }

    public static String getOs() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith(WIN)) {
            return WIN;
        }
        return UNIX;
    }

    public static String getOsCli() {
        return getOs().equals(WIN) ? WIN_CLI: UNIX_CLI;
    }

    public static String getOsEncoding() {
        return getOs().equals(WIN) ? GBK: StandardCharsets.UTF_8.toString();
    }

    public static String getOSPath(String in) {
        return getOs().equals(WIN) ? in.replace("/", "\\"): in;
    }

    public static void runCmd(String path, String cmd) {
        logger.info("[{}] 执行命令 {}", I, cmd);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder
                    .directory(new File(path).getAbsoluteFile())
                    .command(getOsCli(), "-c", cmd)
                    .redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), getOsEncoding()));
            String line;
            boolean needShowLog = false;
            while ((line = br.readLine()) != null) {
                if (needShowLog) {
                    logger.info(line);
                }
                if (line.contains("Liquibase Community 4.12.0 by Liquibase")) {
                    needShowLog = true;
                }
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
